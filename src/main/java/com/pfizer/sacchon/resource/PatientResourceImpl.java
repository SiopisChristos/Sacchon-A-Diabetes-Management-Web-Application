package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.ServerResource;

public class PatientResourceImpl extends ServerResource implements PatientResource {
    @Override
    public PatientResource getPatient() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public PatientResource createPatient(PatientRepresentation patient) {
        return null;
    }
}
