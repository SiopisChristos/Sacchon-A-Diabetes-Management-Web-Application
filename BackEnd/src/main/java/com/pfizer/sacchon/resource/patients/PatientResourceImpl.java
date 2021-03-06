package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
//import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class    PatientResourceImpl extends ServerResource
        implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long id;
    private PatientRepository patientRepository;
    private RecordsRepository recordsRepository;
    private EntityManager em;

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            em = JpaUtil.getEntityManager();
            patientRepository =
                    new PatientRepository(em);
            recordsRepository = new RecordsRepository(em);

        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

        LOGGER.info("Initialising patient resource ends");
    }


    /**
     * Set a patient as inActive
     *
     * @return true if patient deleted successfully
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<Boolean> deletePatient()
            throws NotFoundException {
        LOGGER.finer("Delete patient");
        String usernameLoggedIn = ResourceAuthorization.currentUserToUsername();
        try {
            Patient patient = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(usernameLoggedIn), this, LOGGER);
            Patient p = new Patient();
            p.setActive(false);
            boolean result = patientRepository.removePatient(patient);
            return new RepresentationResponse(200, Constants.CODE_200,
                    result);

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a patient", ex);
            throw new ResourceException(ex);
        }
    }

    @Override
    public RepresentationResponse<List[]> getMyData() throws BadEntityException {
        LOGGER.finer("Get my Data");
        String usernameLoggedIn = ResourceAuthorization.currentUserToUsername();
        try{
            Patient patient = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(usernameLoggedIn), this, LOGGER);
            List<CarbRepresentation> carbs = recordsRepository.getPatientCarbs(patient.getId());
            List<GlucoseRepresentation> glucose = recordsRepository.getPatientGlucose(patient.getId());
            return new RepresentationResponse(200,Constants.CODE_200,new List[]{carbs,glucose});
        }catch (Exception e){
            e.printStackTrace();
            return new RepresentationResponse(500,Constants.CODE_500,false);
        }
    }

    /**
     * Update data of an existing patient
     *
     * @param patientRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Override
    public RepresentationResponse<Boolean> updatePatient
    (PatientRepresentation patientRepresentationIn)
            throws BadEntityException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        ResourceValidator.notNull(patientRepresentationIn);
        LOGGER.finer("Update Patient - Validations Passed");
        String usernameLoggedIn = ResourceAuthorization.currentUserToUsername();
        try {
            Patient patient = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(usernameLoggedIn),this,LOGGER);
            Patient patientIn = PatientRepresentation
                    .updatePatient(patient, patientRepresentationIn);
            boolean result = patientRepository.updatePatient(patientIn);


                return new RepresentationResponse<>(200, Constants.CODE_200, result);

        } catch (Exception ex1) {
            {
                throw new
                        BadEntityException(" Patient has not been created");
            }
        }
    }


    /**
     * Add a new patient user into system
     *
     * @param patientRepresentationIn
     * @return patient as patientRepresentation
     * @throws BadEntityException
     */
    @Override
    public RepresentationResponse<PatientRepresentation> addPatient
    (PatientRepresentation patientRepresentationIn)
            throws BadEntityException {
        LOGGER.finer("Add a new patient into system.");
        ResourceValidator.notNull(patientRepresentationIn);
        ResourceValidator.validatePatient(patientRepresentationIn);
        // Check entity
        LOGGER.finer("Patient checked");
        try {
            // Convert PatientRepresentation to Patient
            Patient patientIn = patientRepresentationIn.createPatient();
            Optional<Patient> patientOut =
                    patientRepository.save(patientIn);
            Patient patient = null;
            if (patientOut.isPresent())
                patient = patientOut.get();
            else
                throw new BadEntityException(" Patient has not been created");
            PatientRepresentation result = new PatientRepresentation(patient);
//            result.setUri("http://localhost:9000/v1/patient/" + patient.getId());
//            getResponse().setLocationRef(
//                    "http://localhost:9000/v1/patient/" + patient.getId());
//            getResponse().setStatus(Status.SUCCESS_CREATED);
            LOGGER.finer("Patient successfully added.");
            return new RepresentationResponse<>(200, "OK", result);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);
            throw new ResourceException(ex);
        }

    }


}