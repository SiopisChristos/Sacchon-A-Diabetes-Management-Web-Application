package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
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


    /**
     * Add a new doctor user into system
     *
     * @param doctorRepresentationIn
     * @return doctor as doctorRepresentation
     * @throws BadEntityException
     */
    @Override
    public RepresentationResponse<DoctorRepresentation> addDoctor
    (DoctorRepresentation doctorRepresentationIn)
            throws BadEntityException {
        LOGGER.finer("Add a new doctor into system.");
        ResourceValidator.notNull(doctorRepresentationIn);
        ResourceValidator.validateDoctor(doctorRepresentationIn);
        // Check entity
        LOGGER.finer("Doctor checked");
        try {
            // Convert DoctorRepresentation to Doctor
            Doctor doctorIn = doctorRepresentationIn.createDoctor();
            Optional<Doctor> doctorOut =
                    doctorRepository.save(doctorIn);
            Doctor doctor = null;
            if (doctorOut.isPresent())
                doctor = doctorOut.get();
            else
                throw new BadEntityException(" Doctor has not been created");
            DoctorRepresentation result = new DoctorRepresentation(doctor);
            LOGGER.finer("Doctor successfully added.");
            return new RepresentationResponse<>(200, "OK", result);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);
            throw new ResourceException(ex);
        }

    }
}