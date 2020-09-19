package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;

import java.util.List;

public interface DoctorResource {

    @Get("json")
    List<PatientRepresentation> getFreePatients();
//    public DoctorRepresentation getDoctor() throws NotFoundException;

    @Delete
    void removeDoctor() throws NotFoundException;



}
//    @Get("json")
//    List<PatientRepresentation> findMyPatients(DoctorRepresentation doctorReprIn) throws NotFoundException;
//
//    @Get("json")
//    List patientRecord(PatientRepresentation patientReprIn) throws NotFoundException;
//
//    @Post("json")
//    boolean postNote(NoteRepresentation noteReprIn) throws NotFoundException;
//
//    @Put("json")
//    boolean updateNote(NoteRepresentation noteReprIn) throws NotFoundException;
//}
