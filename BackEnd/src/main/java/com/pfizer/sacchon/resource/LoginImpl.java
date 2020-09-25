package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.UserTable;
import com.pfizer.sacchon.repository.ChiefRepository;
import com.pfizer.sacchon.repository.UserTableRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.LoginRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.chiefDoctors.PatientsNoActivityImpl;
import com.pfizer.sacchon.resource.constant.Constants;
import org.restlet.Server;
import org.restlet.engine.Engine;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LoginImpl extends ServerResource {


    public static final Logger LOGGER = Engine.getLogger(LoginImpl.class);
    private UserTableRepository userTableRepository;
    private EntityManager entityManager;


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

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initializing LoginActivity ends");
    }

    @Get("Json")
    public RepresentationResponse<Boolean> loginActivity(LoginRepresentation loginRepresentation) {
        LOGGER.info("Starting Get - Login Request");
        try {
            UserTable userToLogin = loginRepresentation.createUser();

            //Throws NotFoundException
            boolean result = userTableRepository.matchUsernameAndPassword(userToLogin.getUsername(), userToLogin.getPassword());

            return new RepresentationResponse(200, Constants.CODE_200, result);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, false);
        }
    }


}
