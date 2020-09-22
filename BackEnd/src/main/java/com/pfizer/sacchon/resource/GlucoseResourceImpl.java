package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.repository.GlucoseRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
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

public class GlucoseResourceImpl extends ServerResource implements GlucoseResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private GlucoseRepository glucoseRepository;
    private EntityManager entityManager;
    private long id;

    @Override
    protected void doRelease(){
        entityManager.close();
    }

    /**
     *Initialises Glucose repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising glucose entry resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            glucoseRepository = new GlucoseRepository(entityManager) ;
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception ex)
        {
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
    public GlucoseRepresentation storeGlucoseEntry(GlucoseRepresentation glucoseRepresentationIn) throws BadEntityException {
        LOGGER.finer("Update a Glucose entry.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a Glucose.");

        // Check given entity
        ResourceValidator.notNull(glucoseRepresentationIn);
//        ResourceValidator.validate(carbReprIn);
        LOGGER.finer("Glucose entry checked");
        try {
            // Convert GlucoseRepresentation to Glucose
            Glucose glucoseIn = glucoseRepresentationIn.createGlucose();
            glucoseIn.setId(id);

            Optional<Glucose> glucoseOut;
            Optional<Glucose> optionalGlucose = glucoseRepository.findById(id);
            setExisting(optionalGlucose.isPresent());

            // If glucose entry exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update glucose entry.");

                // Update glucose in DB and retrieve the new one.
                glucoseOut = glucoseRepository.updateGlucose(glucoseIn);

                // Check if retrieved glucose entry is not null : if it is null it
                // means that the id is wrong.
                if (!glucoseOut.isPresent()) {
                    LOGGER.finer("glucose entry does not exist.");
                    throw new NotFoundException("glucose entry with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException("glucose entry with the following id does not exist: " + id);
            }
            LOGGER.finer("glucose entry successfully updated.");
            return new GlucoseRepresentation(glucoseOut.get());
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
        try {
            id = Long.parseLong(getAttribute("id"));
            if (glucoseRepository.findById(id).isPresent()) {
                boolean p = glucoseRepository.removeGlucoseEntry(id);
                return new RepresentationResponse<>(200,"Glucose entry removed",p);
            }
        } catch (Exception ex1) {
            {
                return new RepresentationResponse<Boolean>(400,"Problem while deleting glucose entry",false);
            }
        }
        return new RepresentationResponse<Boolean>(400,"Problem while deleting glucose entry",false);
    }
}
