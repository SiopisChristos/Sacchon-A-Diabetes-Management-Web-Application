package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientSubmissions {
    @Get("json")
    RepresentationResponse<List> patientData();
}
