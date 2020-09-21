package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class DoctorsNoActivityImpl extends ServerResource implements DoctorsNoActivity {

    public static final Logger LOGGER = Engine.getLogger(DoctorsNoActivityImpl.class);
    private DoctorRepository doctorRepository;
    private EntityManager entityManager;
    private Date startDate;
    private Date endDate;

    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initializing doctorsNoActivity starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            doctorRepository = new DoctorRepository(entityManager);

            try {
                String startDateString = getQueryValue("from");
                String   endDateString = getQueryValue("to");
                String[] words = startDateString.split("-");

                startDate = new Date(Integer.parseInt(words[0])-1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );

                words = endDateString.split("-");
                endDate = new Date(Integer.parseInt(words[0])-1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );

                System.out.println("The Dates are: " + startDate +"\n" + endDate);
            }
            catch(Exception e)
            {
                startDate = null;
                endDate = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initializing doctorsNoActivity ends");
    }


    @Override
    public RepresentationResponse<List<DoctorRepresentation>> doctorsWithNoActivity() {
        LOGGER.finer("Return a doctor list with no activity.");
        try {
            if (startDate == null || endDate == null)
                throw new BadEntityException("The input dates are not valid");

            ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);

            Set<Doctor> doctorIdWithNoActivity = doctorRepository.findDoctorsWithNoActivity(startDate, endDate);

            List<DoctorRepresentation> doctorRepresentation = new ArrayList<>();
            doctorIdWithNoActivity.forEach(x -> doctorRepresentation.add(new DoctorRepresentation(x)));

            return new RepresentationResponse(200, Constants.CODE_200, doctorRepresentation);
        } catch (ResourceException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, Constants.RESPONSE_500);
        }
    }
}
