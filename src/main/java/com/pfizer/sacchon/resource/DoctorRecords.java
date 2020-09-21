package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;

import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import java.util.List;

public interface DoctorRecords {


    @Get("json")
    RepresentationResponse<List[]> patientAllRecords() throws ResourceException;

    @Post("json")
    RepresentationResponse<String> postNote(NoteRepresentation noteReprIn) throws ResourceException;

    @Put("json")
    RepresentationResponse<String> updateNote(NoteRepresentation noteReprIn) throws ResourceException;
}
