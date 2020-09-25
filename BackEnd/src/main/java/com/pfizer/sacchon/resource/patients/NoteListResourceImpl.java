package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.RecordsRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NoteListResourceImpl extends ServerResource implements NoteLIstResource {

    public static final Logger LOGGER = Engine.getLogger(NoteListResourceImpl.class);

    private RecordsRepository recordsRepository;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising note resource starts");
        try {
            recordsRepository = new RecordsRepository(JpaUtil.getEntityManager());
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {

        }
        LOGGER.info("Initialising note resource ends");
    }

    public List<NoteRepresentation> getConsultations() throws NotFoundException {

        LOGGER.finer("Select all notes.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Patient p = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(username), this, this.LOGGER);
            List<Note> notes = recordsRepository.findAllConsultations(p);
            List<NoteRepresentation> result = new ArrayList<>();
            notes.forEach(note -> result.add(new NoteRepresentation(note)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("notes not found");
        }
    }
}
