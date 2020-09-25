package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.CarbRepository;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.chiefDoctors.PatientSubmissions;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class CarbResourceImpl extends ServerResource implements CarbResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private CarbRepository carbRepository;
    private EntityManager entityManager;
    private long id;
    private PatientRepository patientRepository;

    @Override
    protected void doRelease() {
        entityManager.close();
    }

    /**
     * Initialises Carb repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising carb entry resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            patientRepository = new PatientRepository(entityManager);
            carbRepository = new CarbRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Initialising carb entry resource ends");
    }

    /**
     * Update data of an existing carb entry
     *
     * @param carbRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Override
    public RepresentationResponse updateCarbEntry(CarbRepresentation carbRepresentationIn) throws BadEntityException {
        LOGGER.finer("Update a carb entry.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        LOGGER.finer("User allowed to update a carb.");

        // Check given entity
        ResourceValidator.notNull(carbRepresentationIn);
//        ResourceValidator.validate(carbRepresentationIn);
        LOGGER.finer("Carb entry checked");
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Carb carbOld = EntityUtil.getFromOptionalEntity(carbRepository.findById(id), this, LOGGER);
            ResourceValidator.checkCarbIntegrity(carbOld, username);

            carbOld.setGram(carbRepresentationIn.getGram());

            // Update product in DB and retrieve the new one.
            boolean result = carbRepository.updateCarb(carbOld);

            LOGGER.finer("Carb entry successfully updated.");
            return new RepresentationResponse(204, Constants.CODE_204, Constants.RESPONSE_204);
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    /**
     * Delete a carb entry from the Database
     *
     * @return RepresentationResponse
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<Boolean> removeCarbEntry() throws NotFoundException {

        String usernameLoggedIn = ResourceAuthorization.currentUserToUsername();
        try {
            Carb carb = EntityUtil.getFromOptionalEntity(carbRepository.findById(id), this, LOGGER);
            ResourceValidator.checkCarbIntegrity(carb, usernameLoggedIn);
            boolean p = carbRepository.removeCarbEntry(id);
            return new RepresentationResponse(200, Constants.CODE_200, true);

        } catch (Exception e) {
            e.printStackTrace();
            return new RepresentationResponse<Boolean>(400, "Problem while deleting carb entry", false);

        }

    }
}
