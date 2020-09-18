package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.NoteRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface NoteLIstResource {

    @Get("json")
    List<NoteRepresentation> getNotes() throws NotFoundException;
}
