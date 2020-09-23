package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.GlucoseRepository;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.resource.util.ResourceValidator;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.pfizer.sacchon.repository.util.EntityUtil.findEntityById;
import static com.pfizer.sacchon.repository.util.EntityUtil.getFromOptionalEntity;

public class GlucoseResourceImpl extends ServerResource implements GlucoseResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private GlucoseRepository glucoseRepository;
    private PatientRepository patientRepository;
    private EntityManager entityManager;
    private long id;

    @Override
    protected void doRelease() {
        entityManager.close();
    }

    /**
     * Initialises Glucose repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising glucose entry resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            patientRepository = new PatientRepository(entityManager);
            glucoseRepository = new GlucoseRepository(entityManager);
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Initialising glucose entry resource ends");
    }

    /**
     * Update data of an existing glucose entry
     *
     * @param glucoseRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Override
    public Boolean updateGlucoseEntry(GlucoseRepresentation glucoseRepresentationIn) throws BadEntityException {
        LOGGER.finer("Update a Glucose entry.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a Glucose.");

        // Check given entity
        ResourceValidator.notNull(glucoseRepresentationIn);
//        ResourceValidator.validate(carbReprIn);
        LOGGER.finer("Glucose entry checked");
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Glucose oldGlucose = getFromOptionalEntity(
                    findEntityById(new Glucose(), entityManager, id),
                    this,
                    LOGGER);
            Patient patient = getFromOptionalEntity(patientRepository.findPatientByUsername(username),
                    this,
                    LOGGER);


            // Convert GlucoseRepresentation to Glucose
            Glucose newGlucose = glucoseRepresentationIn.createGlucose(patient);
            ResourceValidator.checkGlucoseIntegrity(oldGlucose, patient);
            newGlucose.setId(id);
            newGlucose.setDateTime(oldGlucose.getDateTime());

            Boolean updated = glucoseRepository.updateGlucose(newGlucose);

            return updated;
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    /**
     * Delete a glucose entry from the Database
     *
     * @return RepresentationResponse
     * @throws NotFoundException
     */
    @Override
    public RepresentationResponse<Boolean> removeGlucoseEntry() throws NotFoundException {
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Glucose glucose = EntityUtil.getFromOptionalEntity(glucoseRepository.findById(id), this, this.LOGGER);
            if (!glucose.getPatient().getUsername().equals(username))
                throw new NotAuthorizedException("It's not your measurement!");

            boolean result = glucoseRepository.removeGlucoseEntry(id);

            return new RepresentationResponse<>(200, "Glucose entry removed", result);

        } catch (NotAuthorizedException e) {
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (Exception ex1) {
            return new RepresentationResponse<Boolean>(400, "Problem while deleting glucose entry", false);
        }

    }
}
