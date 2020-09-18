package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.repository.NoteRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.NoteRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NoteListResourceImpl extends ServerResource implements NoteLIstResource {

    public static final Logger LOGGER = Engine.getLogger(NoteListResourceImpl.class);

    private NoteRepository noteRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising note resource starts");
        try {
            noteRepository = new NoteRepository(JpaUtil.getEntityManager());
        }
        catch(Exception e)
        {

        }
        LOGGER.info("Initialising note resource ends");
    }

    public List<NoteRepresentation> getNotes() throws NotFoundException {

        LOGGER.finer("Select all notes.");

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_USER);

        try{
            List<Note> notes = noteRepository.findAll();
            List<NoteRepresentation> result = new ArrayList<>();

//            for (Product product :products)
//                result.add (new ProductRepresentation(product));

            notes.forEach(note -> result.add(new NoteRepresentation(note)));

            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("notes not found");
        }
    }
}
