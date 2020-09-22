package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.pfizer.sacchon.repository.util.EntityUtil.getFromOptionalEntity;
import static com.pfizer.sacchon.resource.constant.Constants.CODE_500;
import static com.pfizer.sacchon.resource.constant.Constants.RESPONSE_500;

public class DoctorUtilitiesImpl extends ServerResource implements DoctorUtilities {

    public static final Logger LOGGER = Engine.getLogger(DoctorRecordsImpl.class);
    private DoctorRepository doctorRepository;
    private RecordsRepository recordsRepository;
    private long id;
    private EntityManager entityManager;

    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initializing Doctor Utilities starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            doctorRepository = new DoctorRepository(entityManager);
            recordsRepository = new RecordsRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            e.printStackTrace();
            id = -1;
        }
        LOGGER.info("Initializing Doctor Utilities ends ");
    }


    @Override
    public RepresentationResponse<List<PatientRepresentation>> getMyPatients() {
        LOGGER.finer("Get my patients");
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            String username = ResourceAuthorization.currentUserToUsername();

            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);

            List<Patient> myPatients = doctorRepository.findMyPatients(doctor.getId());
            List<PatientRepresentation> patientsOut = new ArrayList<>();
            myPatients.forEach(x -> patientsOut.add(new PatientRepresentation(x)));
            return new RepresentationResponse(200, Constants.CODE_200, patientsOut);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, CODE_500, RESPONSE_500);
        }
    }
}
