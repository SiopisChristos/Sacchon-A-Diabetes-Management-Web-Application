package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.repository.CarbRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
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
            carbRepository = new CarbRepository (entityManager) ;
//            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception ex)
        {
            throw new ResourceException(ex);
        }
        LOGGER.info("Initialising carb entry resource ends");
    }

    //The patient can store their data carb intake (measured in grams)
    @Override
    public CarbRepresentation addCarbEntry(CarbRepresentation carbRepresentationIn){

        LOGGER.info("Add a new carb entry.");

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_USER);
//        LOGGER.finer("User allowed to add a product.");

        // Check entity
//        ResourceValidator.notNull(productRepresentationIn);
//        ResourceValidator.validate(productRepresentationIn);
//        LOGGER.finer("Product checked");

        try {
            // Converts CarbRepresentation to Carb
            Carb carbIn = new Carb();
            carbIn.setGram(carbRepresentationIn.getGram());
            carbIn.setDate(carbRepresentationIn.getDate());
            carbIn.setPatient(carbRepresentationIn.getPatient());

            Optional<Carb> carbOut = carbRepository.save(carbIn);
            Carb carb = null;
            if (carbOut.isPresent())
                carb = carbOut.get();

            CarbRepresentation result = new CarbRepresentation();
            result.setGram(carb.getGram());
            result.setDate(carb.getDate());
            result.setPatient(carb.getPatient());
            result.setUri("http://localhost:9000/v1/patient/carb/" + carb.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/patient/carb/" + carb.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.info("Carb entry successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a new carb entry", ex);
            throw new ResourceException(ex);
        }
    }
}
