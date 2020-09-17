package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DoctorNoteImpl extends ServerResource implements DoctorNote {


    public static final Logger LOGGER = Engine.getLogger(DoctorNoteImpl.class);
    private long id;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
        try {

            id = Long.parseLong(getAttribute("id"));

        } catch (Exception e) {
            id = -1;
        }

        LOGGER.info("Initialising product resource ends");
    }


    @Override
    public List patientRecord(PatientRepresentation patientReprIn) throws NotFoundException {
        LOGGER.info("Retrieve a patient record");

        DoctorRepository doctorRepository =
                new DoctorRepository(JpaUtil.getEntityManager());

//        List[] <List> patientData = doctorRepository.getPatientRecord(id);
//        List[] patientDataRepresentation = new List[patientData.length];
//        patientDataRepresentation[0] = (List) patientData[0].forEach(carb => new CarbRepresentation(carb));


        return null;
    }

    @Override
    public boolean postNote(NoteRepresentation noteReprIn) throws BadEntityException, NotAuthorizedException {
        LOGGER.info("Post a note");
        try {
            DoctorRepository doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager());

            Optional<Patient> oPatient = doctorRepository
                    .findPatientById(noteReprIn.getPatient_id());
            setExisting(oPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("Patient id does not exist");
                throw new BadEntityException("Patient id does not exist");
            }

            Optional<Doctor> oDoctor = doctorRepository
                    .findDoctorById(noteReprIn.getDoctor_id());

            setExisting(oDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("Doctor id does not exist");
                throw new BadEntityException("Doctor id does not exist");
            }

            // Check authorization
            boolean authorized =
                    ResourceValidator.userAuthorization(oDoctor.get().getUsername());
            if (!authorized) {
                throw new NotAuthorizedException("Access: DENIED!");
            }


            Note noteIn = noteReprIn.createNote(oPatient.get(), oDoctor.get());
            doctorRepository.saveNote(noteIn);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateNote(NoteRepresentation noteReprIn) throws BadEntityException, NotAuthorizedException {
        LOGGER.info("Update a note");
        try {
            DoctorRepository doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager());

            Patient patient = ResourceValidator.getFromOptionalEntityById(
                    doctorRepository.findEntityById(new Patient(),noteReprIn.getPatient_id()), this,LOGGER );

            Doctor doctor = ResourceValidator.getFromOptionalEntityById(
                    doctorRepository.findEntityById(new Doctor(),noteReprIn.getDoctor_id()), this,LOGGER );

            Note oldNote = ResourceValidator.getFromOptionalEntityById(
                    doctorRepository.findEntityById(new Note(), id) , this,LOGGER );


            // Check authorization
            boolean authorized =
                    ResourceValidator.userAuthorization(doctor.getUsername());
            if (!authorized) {
                throw new NotAuthorizedException("Access: DENIED!");
            }


            Note noteIn = noteReprIn.createNote(patient, doctor);
            noteIn.setId(id);

            //Check Note's Integrity
            if (!oldNote.getDoctor().equals(noteIn.getDoctor()))
                throw new NotAuthorizedException("Can't change doctor_id in your Note");
            if (!oldNote.getPatient().equals(noteIn.getPatient()))
                throw new NotAuthorizedException("Can't change patient_id in your Note");

            doctorRepository.updateNote(noteIn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
