package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.repository.GlucoseRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlucoseResourceImpl extends ServerResource implements GlucoseResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private long id;
    private GlucoseRepository glucoseRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising carb resource starts");
        try {
            glucoseRepository = new GlucoseRepository (JpaUtil.getEntityManager()) ;
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception e)
        {
            id =-1;
        }
        LOGGER.info("Initialising product resource ends");
    }

    //The patient can store their data blood glucose level (date, time, measured in mg/dL)
    @Override
    public GlucoseRepresentation addGlucose(GlucoseRepresentation glucoseRepresentationIn){

        LOGGER.finer("Add a new glucose entry.");

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_USER);
//        LOGGER.finer("User allowed to add a product.");

        // Check entity
//        ResourceValidator.notNull(productRepresentationIn);
//        ResourceValidator.validate(productRepresentationIn);
//        LOGGER.finer("Product checked");

        try {
            // Convert GlucoseRepresentation to Glucose
            Glucose glucoseIn = new Glucose();
            glucoseIn.setDateTime(glucoseRepresentationIn.getDateTime());
            glucoseIn.setMeasurement(glucoseRepresentationIn.getMeasurement());

            Optional<Glucose> glucoseOut = glucoseRepository.save(glucoseIn);
            Glucose glucose = null;
            if (glucoseOut.isPresent())
                glucose = glucoseOut.get();

            GlucoseRepresentation result = new GlucoseRepresentation();
            result.setDateTime(glucose.getDateTime());
            result.setMeasurement(glucose.getMeasurement());
            result.setUri("http://localhost:9000/v1/patient/glucose/" + glucose.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/patient/glucose/" + glucose.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Glucose entry successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a new glucose entry", ex);
            throw new ResourceException(ex);
        }
    }
}
