package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
//import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientListResourceImpl extends ServerResource implements PatientListResource {

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);

    private PatientRepository patientRepository ;
    private EntityManager em;
    /**
     * This release method closes the entityManager
     */
    @Override
    protected void doRelease() {
        em.close();
    }
    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            em=JpaUtil.getEntityManager();
            patientRepository =
                    new PatientRepository (em) ;

        }
        catch(Exception ex)
        {
            throw new ResourceException(ex);
        }

        LOGGER.info("Initialising patient resource ends");
    }

    /**
     * Select a list of all Patients
     * @return list of patients
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<List<PatientRepresentation>> getListPatients()
            throws NotFoundException {

        LOGGER.finer("Select all patients.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        try {
            List<Patient> patients =
                    patientRepository.findAll();
            List<PatientRepresentation> result =
                    new ArrayList<>();

            for(Patient p:patients)
                result.add(new PatientRepresentation(p));
            if(result.size()>0) {
                return new RepresentationResponse<>(200, "OK", result);
            }else{
                return new RepresentationResponse<>(404,"NOT FOUND",result);
            }
        } catch (Exception e) {
            throw new NotFoundException("patients not found");
        }
    }

}
