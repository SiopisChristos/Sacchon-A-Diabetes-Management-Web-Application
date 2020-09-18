package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientResourceImpl extends ServerResource implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long id;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository =
                    new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id = -1;
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
    public PatientRepresentation getPatient() throws NotFoundException {
        LOGGER.info("Retrieve a patient");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        // Initialize the persistence layer.
        PatientRepository patientRepository = new PatientRepository(JpaUtil.getEntityManager());

        try {
            Optional<Patient> oPatient = patientRepository.findById(id);
            setExisting(oPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                throw new NotFoundException("No patient with  : " + id);
            } else {
                LOGGER.finer("User allowed to retrieve a patient");
                PatientRepresentation result =
                        new PatientRepresentation(oPatient.get());
                LOGGER.finer("Patient successfully retrieved");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    /**
     * Delete a patient from system
     *
     * @return true if patient deleted successfully
     * @throws NotFoundException
     */
    @Override
    public Boolean deletePatient() throws NotFoundException {
        LOGGER.finer("Delete patient");
        PatientRepository patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        try {
            Patient p=new Patient();
            p.setId(id);
            p.setActive(false);
            patientRepository.updatePatient(p);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a patient", ex);
            throw new ResourceException(ex);
        }
    }

    /**
     * Update data of an existing patient
     * @param patientRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Override
    public Boolean updatePatient(PatientRepresentation patientRepresentationIn) throws BadEntityException {
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        ResourceValidator.notNull(patientRepresentationIn);
        LOGGER.finer("Company checked");
        try {
            Patient patientIn = dataReturn(patientRepresentationIn);
            patientIn.setId(id);
            boolean p = patientRepository.updatePatient(patientIn);
            if (p == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex1) {
            {
                throw new
                        BadEntityException(" Patient has not been created");
            }
        }
    }

    public static Patient dataReturn(PatientRepresentation patientRep) {
        Patient patientIn = new Patient();
        if (patientRep.getFirstName() != null) {
            patientIn.setFirstName(patientRep.getFirstName());
        }
        if (patientRep.getLastName() != null) {
            patientIn.setLastName(patientRep.getLastName());
        }
        if (patientRep.getUsername() != null) {
            patientIn.setUsername(patientRep.getUsername());
        }
        if (patientRep.getAddress() != null) {
            patientIn.setAddress(patientRep.getAddress());
        }
        if (patientRep.getCity() != null) {
            patientIn.setCity(patientRep.getCity());
        }
        if (patientRep.getZipCode() != null) {
            patientIn.setZipCode(patientRep.getZipCode());
        }
        if (patientRep.getDateOfBirth() != null) {
            patientIn.setDateOfBirth(patientRep.getDateOfBirth());
        }
        if (patientRep.getPhoneNumber() != null) {
            patientIn.setPhoneNumber(patientRep.getPhoneNumber());
        }
        if (patientRep.getDoctorId() != null) {
            patientIn.setDoctor(patientRep.getDoctorId());
        }
        return patientIn;
    }


    /**
     * Add a new patient user into system
     *
     * @param patientRepresentationIn
     * @return patient as patientRepresentation
     * @throws BadEntityException
     */
    @Override
    public PatientRepresentation addPatient(PatientRepresentation patientRepresentationIn) throws
            BadEntityException {

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
            Patient patientIn = dataReturn(patientRepresentationIn);
            Optional<Patient> patientOut =
                    patientRepository.save(patientIn);
            Patient patient = null;
            if (patientOut.isPresent())
                patient = patientOut.get();
            else
                throw new
                        BadEntityException(" Patient has not been created");
            PatientRepresentation result = initData(patientIn);

            result.setUri("http://localhost:9000/v1/patient/" + patient.getId());
            getResponse().setLocationRef(
                    "http://localhost:9000/v1/patient/" + patient.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);
            LOGGER.finer("Patient successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);

            throw new ResourceException(ex);
        }

    }

    public static PatientRepresentation initData(Patient newPatient) {
        PatientRepresentation result =
                new PatientRepresentation();
        result.setUsername(newPatient.getUsername());
        result.setFirstName(newPatient.getFirstName());
        result.setLastName(newPatient.getLastName());
        result.setAddress(newPatient.getAddress());
        result.setPhoneNumber(newPatient.getPhoneNumber());
        result.setCity(newPatient.getCity());
        result.setDoctorId(newPatient.getDoctor());
        result.setActive(true);
        return result;
    }

    /**
     * Set a patient as inActive
     *
     * @return
     * @throws NotFoundException
     */
    @Override
    public Boolean removePatient() throws NotFoundException {
        try {
            boolean p = patientRepository.removePatient(id);
            if (p == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex1) {
            {
                throw new
                        NotFoundException(" Patient has not been created");
            }
        }
    }

}