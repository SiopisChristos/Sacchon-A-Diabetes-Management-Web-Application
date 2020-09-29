package com.pfizer.sacchon.resource.chiefDoctors;

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
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class GetPatients extends ServerResource {


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
    public RepresentationResponse<List<PatientRepresentation>> getActivePatients(){
        try{
            ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
            List<PatientRepresentation> patients = new ArrayList<>();
            Set<Patient> result = chiefRepository.findPatients_active(new Date());
            result.forEach(p -> patients.add(new PatientRepresentation(p)));
            return new RepresentationResponse<>(200,Constants.CODE_200,patients);
        }catch (Exception e){
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, false);
        }

    }

}
