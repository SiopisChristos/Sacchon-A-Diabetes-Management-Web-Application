package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;

import java.util.Date;
import java.util.List;

public interface DoctorsNoActivity {
    @Get
    RepresentationResponse<List<DoctorRepresentation>> doctorsWithNoActivity();
}
