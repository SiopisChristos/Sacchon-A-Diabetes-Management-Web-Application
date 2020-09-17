package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import java.util.List;

public interface DoctorNote {

    @Get("json")
    List patientRecord(PatientRepresentation patientReprIn) throws NotFoundException;

    @Post("json")
    boolean postNote(NoteRepresentation noteReprIn) throws NotFoundException, BadEntityException, NotAuthorizedException;

    @Put("json")
    boolean updateNote(NoteRepresentation noteReprIn) throws NotFoundException, BadEntityException, NotAuthorizedException;
}
