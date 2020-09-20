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

    @Get("json")
    List<PatientRepresentation> getFreePatients();

    @Delete
    void removeDoctor() throws NotFoundException;

    @Post
    boolean notificationSeen(NoteRepresentation noteReprIn) ;

    @Put
    boolean choosePatient();

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
