package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.logging.Logger;

import static com.pfizer.sacchon.repository.util.EntityUtil.findEntityById;
import static com.pfizer.sacchon.repository.util.EntityUtil.getFromOptionalEntityById;

public class DoctorRecordsImpl extends ServerResource implements DoctorRecords {


    public static final Logger LOGGER = Engine.getLogger(DoctorRecordsImpl.class);
    private DoctorRepository doctorRepository;
    private RecordsRepository recordsRepository;
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


    /**
     * Returns all the patient data (Carbs & Glucose measurements) and its consultations of a specific
     * Patient in a array List structure which contains three Lists.
     * It is accessed only by patient's doctor or if patient has not a doctor by every doctor.
     * The List structure is: {Carbs, Glucose, Notes}
     *
     * @return
     * @throws BadEntityException
     * @throws NotAuthorizedException
     */
    @Override
    public List[] patientAllRecords() throws ResourceException {
        LOGGER.info("Retrieve a patient record");
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager());
            recordsRepository =
                    new RecordsRepository(JpaUtil.getEntityManager());

            Doctor doctor = getFromOptionalEntityById(
                    doctorRepository.findDoctorByUsername(username), this, LOGGER);

            //Safe Guard
            doctorRepository.doctorAccessPatientData(id, doctor);

            List[] patientData = recordsRepository.getPatientRecord(id);
            return patientData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e);
        }
    }


    /**
     * @param noteReprIn
     * @return
     * @throws BadEntityException
     * @throws NotAuthorizedException
     */
    @Override
    public boolean postNote(NoteRepresentation noteReprIn) throws ResourceException {
        LOGGER.info("Post a note");
        try {
            doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager());
            recordsRepository =
                    new RecordsRepository(JpaUtil.getEntityManager());

            Patient patient = getFromOptionalEntityById(
                    findEntityById(new Patient(), JpaUtil.getEntityManager(), noteReprIn.getPatient_id()),
                    this,
                    LOGGER);

            Doctor doctor = getFromOptionalEntityById(
                    findEntityById(new Doctor(), JpaUtil.getEntityManager(), noteReprIn.getDoctor_id()),
                    this,
                    LOGGER);

            ResourceAuthorization.checkUserAuthorization(doctor.getUsername());

            Note noteIn = noteReprIn.createNote(patient, doctor);
            recordsRepository.saveNote(noteIn);

            return true;
        } catch (Exception e) {
            throw new ResourceException(e);
        }

    }

    /**
     * @param noteReprIn
     * @return
     * @throws BadEntityException
     * @throws NotAuthorizedException
     */
    @Override
    public boolean updateNote(NoteRepresentation noteReprIn) throws ResourceException {
        LOGGER.info("Update a note");
        try {
            DoctorRepository doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager());
            RecordsRepository recordsRepository =
                    new RecordsRepository(JpaUtil.getEntityManager());

            Patient patient = getFromOptionalEntityById(
                    findEntityById(new Patient(), JpaUtil.getEntityManager(), noteReprIn.getPatient_id()),
                    this,
                    LOGGER);

            Doctor doctor = getFromOptionalEntityById(
                    findEntityById(new Doctor(), JpaUtil.getEntityManager(), noteReprIn.getDoctor_id()),
                    this,
                    LOGGER);

            Note oldNote = getFromOptionalEntityById(
                    findEntityById(new Note(), JpaUtil.getEntityManager(), id),
                    this,
                    LOGGER);
            Note noteIn = noteReprIn.createNote(patient, doctor);

            ResourceAuthorization.checkUserAuthorization(doctor.getUsername());
            ResourceValidator.checkNoteIntegrity(oldNote, noteIn);

            noteIn.setId(id);
            recordsRepository.updateNote(noteIn);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceException(e);
        }
    }
}
