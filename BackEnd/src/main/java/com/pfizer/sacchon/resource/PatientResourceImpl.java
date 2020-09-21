package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.util.ResourceValidator;
//import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientResourceImpl extends ServerResource
        implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long id;
    private PatientRepository patientRepository;
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

        } catch (Exception ex) {
            throw new ResourceException(ex);
        }

        LOGGER.info("Initialising patient resource ends");
    }

    /**
     * Search for a patient and return it as PatientRepresentation(json)
     *
     * @return patient
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<PatientRepresentation> getPatient()
            throws NotFoundException {
        LOGGER.info("Retrieve a patient");
        id = Long.parseLong(getAttribute("id"));
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        // Initialize the persistence layer.
        try {
            Optional<Patient> oPatient = patientRepository.findById(id);
            setExisting(oPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("Patient id does not exist:" + id);
                return new RepresentationResponse<>(404,
                        "Not Found", null);
            } else {
                LOGGER.finer("User allowed to retrieve a patient");
                PatientRepresentation result =
                        new PatientRepresentation(oPatient.get());
                LOGGER.finer("Patient successfully retrieved");

                return new RepresentationResponse<>(200,
                        "OK", result);
            }
        } catch (Exception ex) {
            throw new NotFoundException("Not found");
        }
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
        id = Long.parseLong(getAttribute("id"));
        PatientRepository patientRepository =
                new PatientRepository(em);
        try {
            Patient p = new Patient();
            p.setActive(false);
            if (patientRepository.removePatient(id))
                return new RepresentationResponse(200, "OK, Patient set as inActive",
                        patientRepository.findById(id));
            else new RepresentationResponse(200, "OK", false);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a patient", ex);
            throw new ResourceException(ex);
        }
        return null;
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
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        ResourceValidator.notNull(patientRepresentationIn);
        LOGGER.finer("Company checked");
        id = Long.parseLong(getAttribute("id"));
        try {
            Patient patientIn = PatientRepresentation
                    .updatePatient(patientRepresentationIn);
            if (patientRepository.updatePatient(patientIn, id)) {
                return new RepresentationResponse<>(200, "OK", true);
            } else {
                return new RepresentationResponse<>(500, "Bad request", false);
            }
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
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to add a patient.");
        //Check validation of InputData-argument
        ResourceValidator.notNull(patientRepresentationIn);
        ResourceValidator.validate(patientRepresentationIn);
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
            PatientRepresentation result = PatientRepresentation.initData(patientIn);
            result.setUri("http://localhost:9000/v1/patient/" + patient.getId());
            getResponse().setLocationRef(
                    "http://localhost:9000/v1/patient/" + patient.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);
            LOGGER.finer("Patient successfully added.");
            return new RepresentationResponse<>(200,"OK",result);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);
            throw new ResourceException(ex);
        }

    }


    /**
     * Delete a patient from system
     *
     * @return
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<Boolean> removePatient() throws NotFoundException {
        try {
            id = Long.parseLong(getAttribute("id"));
            if (patientRepository.findById(id).isPresent()) {
                boolean p = patientRepository.removeFromSystem(id);
                return new RepresentationResponse<>(200,"OK",p);
            }
        } catch (Exception ex1) {
            {
                return new RepresentationResponse<Boolean>(400,"Bad Request",false);
            }
        }
        return new RepresentationResponse<Boolean>(400,"Bad Request",false);
    }

}