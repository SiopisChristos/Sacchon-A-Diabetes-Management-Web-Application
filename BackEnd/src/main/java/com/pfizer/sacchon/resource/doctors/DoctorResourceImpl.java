package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.pfizer.sacchon.repository.util.EntityUtil.*;
import static com.pfizer.sacchon.resource.constant.Constants.*;

public class DoctorResourceImpl extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository;
    private RecordsRepository recordsRepository;
    private PatientRepository patientRepository;
    private long id;
    private EntityManager entityManager;


    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");

        try {
            entityManager = JpaUtil.getEntityManager();
            doctorRepository =
                    new DoctorRepository(entityManager);
            recordsRepository = new RecordsRepository(entityManager);
            patientRepository = new PatientRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            e.printStackTrace();
            id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }



    @Override
    public RepresentationResponse<List<PatientRepresentation>> getFreePatients() {

        LOGGER.finer("Get free patients");

        try {

            List<Patient> patients = doctorRepository.findFreePatients();
            List<PatientRepresentation> patientsOut = new ArrayList<>();
            patients.forEach(x -> patientsOut.add(new PatientRepresentation(x)));
            return new RepresentationResponse(200, Constants.CODE_200, patientsOut);
        } catch (ResourceException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, CODE_500, Constants.RESPONSE_500);
        }


    }


    @Override
    public RepresentationResponse<Boolean> deleteDoctor() throws NotFoundException {
        LOGGER.finer("delete doctor");

        try {
            ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
            String username = ResourceAuthorization.currentUserToUsername();
            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);
            doctorRepository.removeDoctor(doctor.getId());
            return new RepresentationResponse(200, "OK", true);
        } catch (ResourceException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, CODE_403, RESPONSE_403);
        } catch (BadEntityException e) {
            LOGGER.log(Level.WARNING, "Entity error ", e);
            return new RepresentationResponse(400, CODE_400, RESPONSE_400);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a doctor", ex);
            return new RepresentationResponse(500, CODE_500, RESPONSE_500);
        }


    }

    /**
     *
     * @return
     */
    @Override
    public RepresentationResponse<Boolean> notificationSeen() {
        try {

            String systemUsername = ResourceAuthorization.currentUserToUsername();

            //Throws BadEntityException
            Patient patient = getFromOptionalEntity(patientRepository.findPatientByUsername(systemUsername), this, LOGGER);

            Note oldNote = getFromOptionalEntity(
                    findEntityById(new Note(), entityManager, id),
                    this,
                    LOGGER);

            if (!oldNote.getPatient().equals(patient))
                throw new NotAuthorizedException("It's not your note!");


            recordsRepository.updateNoteSeen(oldNote);

            return new RepresentationResponse(200, Constants.CODE_200, "true");
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);

        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, CODE_500, Constants.RESPONSE_500);
        }

    }

    @Override
    public RepresentationResponse<Boolean> choosePatient() {

        try {
            String username = ResourceAuthorization.currentUserToUsername();
            Patient patient = getFromOptionalEntity(
                    findEntityById(new Patient(), entityManager, id),
                    this,
                    LOGGER);

            if (!doctorRepository.isFreePatient(patient.getId()))
                throw new NotAuthorizedException("Patient is assigned to another Doctor");

            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);

            boolean updated = doctorRepository.updatePatientDoctor(doctor, patient);
            return new RepresentationResponse(200, Constants.CODE_200, updated);
        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, CODE_400, RESPONSE_400);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, CODE_500, RESPONSE_500);
        }
    }
}
