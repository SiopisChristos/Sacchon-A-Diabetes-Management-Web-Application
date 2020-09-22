package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.CarbRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
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

public class CarbResourceImpl extends ServerResource implements CarbResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private CarbRepository carbRepository;
    private EntityManager entityManager;
    private long id;

    @Override
    protected void doRelease(){
        entityManager.close();
    }

    /**
     *Initialises Carb repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising carb entry resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            carbRepository = new CarbRepository (entityManager);
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception ex)
        {
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
    public CarbRepresentation storeCarbEntry(CarbRepresentation carbRepresentationIn) throws BadEntityException {
        LOGGER.finer("Update a carb entry.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a carb.");

        // Check given entity
        ResourceValidator.notNull(carbRepresentationIn);
//        ResourceValidator.validate(carbReprIn);
        LOGGER.finer("Carb entry checked");
        try {
            // Convert CarbRepresentation to Carb
            Carb carbIn = carbRepresentationIn.createCarb();
            carbIn.setId(id);

            Optional<Carb> carbOut;
            Optional<Carb> optionalCarb = carbRepository.findById(id);
            setExisting(optionalCarb.isPresent());

            // If carb entry exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update carb entry.");

                // Update product in DB and retrieve the new one.
                carbOut = carbRepository.updateCarb(carbIn);

                // Check if retrieved carb entry is not null : if it is null it
                // means that the id is wrong.
                if (!carbOut.isPresent()) {
                    LOGGER.finer("Carb entry does not exist.");
                    throw new NotFoundException("Carb entry with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException("Carb entry with the following id does not exist: " + id);
            }
            LOGGER.finer("Carb entry successfully updated.");
            return new CarbRepresentation(carbOut.get());
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
        try {
            id = Long.parseLong(getAttribute("id"));
            if (carbRepository.findById(id).isPresent()) {
                boolean p = carbRepository.removeCarbEntry(id);
                return new RepresentationResponse<>(200,"Carb entry removed",p);
            }
        } catch (Exception ex1) {
            {
                return new RepresentationResponse<Boolean>(400,"Problem while deleting carb entry",false);
            }
        }
        return new RepresentationResponse<Boolean>(400,"Problem while deleting carb entry",false);
    }
}
