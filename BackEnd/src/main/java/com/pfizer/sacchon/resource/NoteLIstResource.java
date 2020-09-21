package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.NoteRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface NoteLIstResource {

    /**
     * The patient can view the current and past consultations from doctors
     *
     * @return List of all notes entries
     */
    @Get("json")
    List<NoteRepresentation> getConsultations() throws NotFoundException;
}
