package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public interface DoctorResource {

    @Get("json")
    RepresentationResponse<List<PatientRepresentation>> getFreePatients();

    @Post("json")
    RepresentationResponse <Boolean> notificationSeen() ;

    @Put("json")
    RepresentationResponse <Boolean> choosePatient();

    @Delete("json")
    RepresentationResponse<Boolean>
    deleteDoctor() throws NotFoundException;


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
