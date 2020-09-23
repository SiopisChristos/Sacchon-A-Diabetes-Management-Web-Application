package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import static com.pfizer.sacchon.repository.util.EntityUtil.findEntityById;
import static com.pfizer.sacchon.repository.util.EntityUtil.getFromOptionalEntity;

public class DoctorRecordsImpl extends ServerResource implements DoctorRecords {


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
        LOGGER.info("Initialising product resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            doctorRepository = new DoctorRepository(entityManager);
            recordsRepository = new RecordsRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            e.printStackTrace();
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
    public RepresentationResponse<List[]> patientAllRecords() throws ResourceException {
        LOGGER.info("Retrieve a patient record");
        try {
            String username = ResourceAuthorization.currentUserToUsername();

            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username), this, LOGGER);

            //Safe Guard throws NotAuthorizedException
            doctorRepository.doctorAccessPatientData(id, doctor);

            List[] patientData = recordsRepository.getPatientRecord(id);
            return new RepresentationResponse(200, Constants.CODE_200, patientData);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, Constants.RESPONSE_500);
        }
    }


    /**
     * @param noteReprIn
     * @return
     * @throws BadEntityException
     * @throws NotAuthorizedException
     */
    @Override
    public RepresentationResponse<String> postNote(NoteRepresentation noteReprIn) throws ResourceException {
        LOGGER.info("Post a note");
        try {
            String systemUsername = ResourceAuthorization.currentUserToUsername();

            Patient patient = getFromOptionalEntity(
                    findEntityById(new Patient(), entityManager, noteReprIn.getPatient_id()),
                    this,
                    LOGGER);

            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(systemUsername),
                    this,
                    LOGGER);

            //ResourceAuthorization.checkUserAuthorization(doctor.getUsername());
            if (!doctorRepository.isYourPatient(noteReprIn.getPatient_id(), doctor.getId())) {
                throw new NotAuthorizedException("You're not Authorized to do this action");
            }

            Note noteIn = noteReprIn.createNote(patient, doctor);
            recordsRepository.saveNote(noteIn);

            return new RepresentationResponse(204, Constants.CODE_204, Constants.RESPONSE_204);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse<>(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse<>(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse<>(500, Constants.CODE_500, Constants.RESPONSE_500);
        }

    }

    /**
     * @param noteReprIn
     * @return
     * @throws BadEntityException
     * @throws NotAuthorizedException
     */
    @Override
    public RepresentationResponse<String> updateNote(NoteRepresentation noteReprIn) throws ResourceException {
        LOGGER.info("Update a note");
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Doctor doctor = getFromOptionalEntity(
                    doctorRepository.findDoctorByUsername(username),
                    this,
                    LOGGER);

            Note oldNote = getFromOptionalEntity(
                    findEntityById(new Note(), entityManager, id),
                    this,
                    LOGGER);
            //throws NotAuthorized Exception
            ResourceValidator.checkNoteDoctor(oldNote,doctor);

            //Create the new Note (same as old but added the new Doctor's message)
            Note noteIn = oldNote;
            noteIn.setMessage(noteReprIn.getMessage());

            recordsRepository.updateNote(noteIn);

            return new RepresentationResponse(204, Constants.CODE_204, Constants.RESPONSE_204);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse<>(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse<>(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse<>(500, Constants.CODE_500, Constants.RESPONSE_500);
        }
    }
}
