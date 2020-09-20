package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
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

public class DoctorResourceImpl extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
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
        LOGGER.info("Initialising doctor resource starts");

        try {
            entityManager = JpaUtil.getEntityManager();
            doctorRepository =
                    new DoctorRepository(entityManager);
            recordsRepository = new RecordsRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            e.printStackTrace();
            id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }


//    @Override
//    public DoctorRepresentation getDoctor() throws NotFoundException {
//
//        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
//        Doctor doctor;
//        try {
//
//            Optional<Doctor> thedoctor = doctorRepository.findDoctorById(id);
//
//            setExisting(thedoctor.isPresent());
//            if (!isExisting()) {
//                LOGGER.config("doctor id does not exist:" + id);
//                throw new NotFoundException("No doctor with  : " + id);
//            } else {
//                doctor = thedoctor.get();
//                LOGGER.finer("User allowed to retrieve a doctor");
//                DoctorRepresentation result =
//                        new DoctorRepresentation(doctor);
//
//                LOGGER.finer("Doctor successfully retrieved");
//
//                return result;
//            }
//        } catch (Exception ex) {
//            throw new ResourceException(ex);
//        }
//    }

    @Override
    public List<PatientRepresentation> getFreePatients() {

        LOGGER.finer("Get free patients");
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            List<Patient> patients = doctorRepository.findFreePatients();
            List<PatientRepresentation> patientsOut = new ArrayList<>();
            patients.forEach(x -> patientsOut.add(new PatientRepresentation(x)));
            return patientsOut;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    @Override
    public RepresentationResponse<Boolean> deleteDoctor() throws NotFoundException {
        LOGGER.finer("delete doctor");
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            String username = ResourceAuthorization.currentUserToUsername();
            Doctor doctor = getFromOptionalEntity(
                     doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);
            doctorRepository.removeDoctor(doctor.getId());
            return new RepresentationResponse(200, "OK", true);

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a doctor", ex);
           return new RepresentationResponse(200, "OK", false);
        }

    }

    @Override
    public boolean notificationSeen(NoteRepresentation noteReprIn) {
        try {

            String systemUsername = ResourceAuthorization.currentUserToUsername();

            //Throws BadEntityException
            Patient patient = getFromOptionalEntity(
                    findEntityById(new Patient(), entityManager, noteReprIn.getPatient_id()),
                    this,
                    LOGGER);
            Doctor doctor = getFromOptionalEntity(
                    findEntityById(new Doctor(), entityManager, noteReprIn.getDoctor_id()),
                    this,
                    LOGGER);
            Note oldNote = getFromOptionalEntity(
                    findEntityById(new Note(), entityManager, id),
                    this,
                    LOGGER);

            Note noteIn = noteReprIn.createNote(patient, doctor);

            //Throws a NotAuthorized Exception
            ResourceAuthorization.equalsUsername(systemUsername, patient.getUsername());
            ResourceValidator.checkNoteIntegrity(oldNote, noteIn);

            noteIn.setId(id);
            recordsRepository.updateNoteSeen(noteIn);

            return true;
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return false;
        } catch (BadEntityException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean choosePatient() {

        try {
            //ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
            String username = ResourceAuthorization.currentUserToUsername();
            Patient patient = getFromOptionalEntity(
                    findEntityById(new Patient(), entityManager, id),
                    this,
                    LOGGER);

            if (!doctorRepository.isFreePatient(patient.getId()))
                throw new BadEntityException("Patient is assigned to another Doctor");

            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);

            boolean updated = doctorRepository.updatePatientDoctor(doctor, patient);
            return updated;

        } catch (BadEntityException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
}
