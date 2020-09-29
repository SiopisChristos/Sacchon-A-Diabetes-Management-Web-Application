package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.ChiefRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.DoctorRepresentation;
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

public class GetDoctors extends ServerResource {


    public static final Logger LOGGER = Engine.getLogger(GetDoctors.class);
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
    public RepresentationResponse<List<PatientRepresentation>> getActiveDoctors(){
        try{
            ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
            List<DoctorRepresentation> doctors = new ArrayList<>();
            Set<Doctor> result = chiefRepository.findDoctors_active(new Date());
            result.forEach(d -> doctors.add(new DoctorRepresentation(d)));
            return new RepresentationResponse(200, Constants.CODE_200,doctors);
        }catch (Exception e){
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, false);
        }

    }

}