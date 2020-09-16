package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientListResourceImpl extends ServerResource implements PatientListResource {


    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);


    private PatientRepository patientRepository ;


    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository =
                    new PatientRepository (JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising patient resource ends");
    }





    @Override
    public List<PatientRepresentation> getListPatients() throws NotFoundException {

        LOGGER.finer("Select all patients.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);

        try {

            List<Patient> patients =
                    patientRepository.findAll();
            List<PatientRepresentation> result =
                    new ArrayList<>();




            patients.forEach(patient ->
                    result.add(new PatientRepresentation(patient)));


            return result;
        } catch (Exception e) {
            throw new NotFoundException("patients not found");
        }


    }
}
