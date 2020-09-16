package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.NoteRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import javassist.NotFoundException;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.logging.Logger;

public class DoctorResourceImpl extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    @Override
    public boolean deleteDoctor(DoctorRepresentation doctorReprIn) throws NotFoundException {
        return false;
    }

    @Override
    public List<PatientRepresentation> findInactivePatients() throws NotFoundException {
        return null;
    }

    @Override
    public List patientRecord(PatientRepresentation patientReprIn) throws NotFoundException {
        return null;
    }

    @Override
    public List<PatientRepresentation> findMyPatients(DoctorRepresentation doctorReprIn) throws NotFoundException {
        return null;
    }

    @Override
    public boolean postNote(NoteRepresentation noteReprIn) throws NotFoundException {

        return false;
    }

    @Override
    public boolean updateNote(NoteRepresentation noteReprIn) throws NotFoundException {
        return false;
    }
}
