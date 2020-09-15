package com.pfizer.sacchon.resource;
import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.resource.util.ResourceValidator;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PatientResourceImpl extends ServerResource implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long id;
    private PatientRepository patientRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository =
                    new PatientRepository (JpaUtil.getEntityManager()) ;
            id = Long.parseLong(getAttribute("id"));

        }
        catch(Exception e)
        {
            id =-1;
        }

        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public PatientRepresentation getPatient() throws NotFoundException {
        LOGGER.info("Retrieve a patient");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        // Initialize the persistence layer.
        PatientRepository patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Patient patient;
        try {


            Optional<Patient> opatient = patientRepository.findById(id);


            setExisting(opatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                throw new NotFoundException("No patient with  : " + id);
            } else {
                patient = opatient.get();
                LOGGER.finer("User allowed to retrieve a patient");
                //System.out.println(  userId);
                PatientRepresentation result =
                        new PatientRepresentation(patient);


                LOGGER.finer("Patient successfully retrieved");

                return result;

            }


        } catch (Exception ex) {
            throw new ResourceException(ex);
        }


    }

    @Override
    public void remove()  {

    }

    @Override
    public PatientRepresentation createPatient(PatientRepresentation patientRepresentationIn)  throws BadEntityException {

        LOGGER.finer("Add a new patient into system.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        LOGGER.finer("User allowed to add a patient.");

        // Check entity

        ResourceValidator.notNull(patientRepresentationIn);
        ResourceValidator.validate(patientRepresentationIn);

        LOGGER.finer("Patient checked");

        try {

            // Convert CompanyRepresentation to Company
            Patient patientIn = new Patient();


            patientIn.setUsername(patientRepresentationIn.getUsername());
            patientIn.setFirstName(patientRepresentationIn.getFirstName());
            patientIn.setLastName(patientRepresentationIn.getLastName());
            patientIn.setPhoneNumber(patientRepresentationIn.getPhoneNumber());

            Optional<Patient> patientOut =
                    patientRepository.save(patientIn);
            Patient patient= null;
            if (patientOut.isPresent())
                patient = patientOut.get();
            else
                throw new
                        BadEntityException(" Patient has not been created");

            PatientRepresentation result =
                    new PatientRepresentation();
            result.setUsername(patientRepresentationIn.getUsername());
            result.setFirstName(patientRepresentationIn.getFirstName());
            result.setLastName(patientRepresentationIn.getLastName());;
            result.setZipCode("23");
            result.setPhoneNumber("4234");

            result.setUri("http://localhost:9000/v1/patient/"+patient.getId());

            getResponse().setLocationRef(
                    "http://localhost:9000/v1/patient/"+patient.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Patient successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);

            throw new ResourceException(ex);
        }



    }

}
