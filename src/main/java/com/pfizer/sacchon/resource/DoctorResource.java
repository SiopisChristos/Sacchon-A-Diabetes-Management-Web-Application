package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import java.util.List;

public interface DoctorResource {

    @Delete
    boolean deleteDoctor(DoctorRepresentation doctorReprIn) throws NotFoundException;

    @Get("json")
    List<PatientRepresentation> findInactivePatients() throws NotFoundException;

    @Get("json")
    List<PatientRepresentation> findMyPatients(DoctorRepresentation doctorReprIn) throws NotFoundException;

    @Get("json")
    List patientRecord(PatientRepresentation patientReprIn) throws NotFoundException;

    @Post("json")
    boolean postNote(NoteRepresentation noteReprIn) throws NotFoundException;

    @Put("json")
    boolean updateNote(NoteRepresentation noteReprIn) throws NotFoundException;
}
