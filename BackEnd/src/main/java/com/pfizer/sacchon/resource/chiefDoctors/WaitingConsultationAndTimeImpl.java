package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.ChiefRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class WaitingConsultationAndTimeImpl extends ServerResource {


    public static final Logger LOGGER = Engine.getLogger(WaitingConsultationAndTimeImpl.class);
    private ChiefRepository chiefRepository;
    private EntityManager entityManager;


    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initializing patientsNoActivity starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            chiefRepository = new ChiefRepository(entityManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initializing doctorsNoActivity ends");
    }


    @Get("json")
    public RepresentationResponse<List[]> getPatientsWaitingAndTime(){
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
            List[] result ;
            List<PatientRepresentation> patients = new ArrayList<>();
            result = chiefRepository.calculatePatientsDatesWithoutDoctor();
            //Convert Patients to their Representation
            result[0].forEach( patient -> patients.add(new PatientRepresentation((Patient) patient)));
            result[0] = patients;
            return new RepresentationResponse(200,Constants.CODE_200, result);
        } catch (ResourceException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (
                Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, Constants.RESPONSE_500);
        }
    }
}
