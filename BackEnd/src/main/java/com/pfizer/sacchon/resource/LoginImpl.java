package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.model.UserTable;
import com.pfizer.sacchon.repository.ChiefRepository;
import com.pfizer.sacchon.repository.UserTableRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.LoginRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.representation.UserRegistrationRepr;
import com.pfizer.sacchon.resource.chiefDoctors.PatientsNoActivityImpl;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import org.restlet.Server;
import org.restlet.engine.Engine;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LoginImpl extends ServerResource {


    public static final Logger LOGGER = Engine.getLogger(LoginImpl.class);
    private UserTableRepository userTableRepository;
    private EntityManager entityManager;
    private String username;
    private String password;


    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initializing LoginActivity starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            userTableRepository = new UserTableRepository(entityManager);
            username = getQueryValue("username");
            password = getQueryValue("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initializing LoginActivity ends");
    }

    @Get("Json")
    public RepresentationResponse<Boolean> loginActivity() {
        LOGGER.info("Starting Get - Login Request");
        try {
            LoginRepresentation loginRepresentation = new LoginRepresentation(username, password);
            UserTable userToLogin = loginRepresentation.createUser();

            //Throws NotFoundException
            String resultRole = userTableRepository.matchUsernameAndPassword(userToLogin.getUsername(), userToLogin.getPassword());

            return new RepresentationResponse(200, Constants.CODE_200, resultRole);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, false);
        }
    }


    @Post("Json")
    public RepresentationResponse<Boolean> register(UserRegistrationRepr userRegistrationRepr) {
        LOGGER.info("Starting Post - Register Request");
        try {
            //throws a BadEntityException
            ResourceValidator.validateUserRegister(userRegistrationRepr);
            UserTable user;
            Boolean result;
            String role = userRegistrationRepr.getRole();
            if (role.equals("patient")) {
                Patient patient = userRegistrationRepr.createPatient();
                user = userRegistrationRepr.createUser();
                result = userTableRepository.register(patient, user);
                return new RepresentationResponse(200, Constants.CODE_200, result);
            } else if (role.equals("doctor")) {
                Doctor doctor = userRegistrationRepr.createDoctor();
                user = userRegistrationRepr.createUser();
                result = userTableRepository.register(doctor, user);
                return new RepresentationResponse(200, Constants.CODE_200, result);
            } else
                throw new BadEntityException("Role is not recognized");
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, e.getMessage());
        }

    }


}
