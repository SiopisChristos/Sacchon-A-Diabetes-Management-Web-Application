package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarbListResourceImpl extends ServerResource implements CarbListResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private CarbRepository carbRepository;
    private EntityManager entityManager;
    private Date startDate;
    private Date endDate;
    private Date date;

    /**
     * This release method closes the entityManager
     */
    @Override
    protected void doRelease() {
        entityManager.close();
    }

    /**
     * Initializes the carb repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising carb resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            carbRepository = new CarbRepository(entityManager);
            try {
                String startDateString = getQueryValue("from");
                String   endDateString = getQueryValue("to");

                String[] words = startDateString.split("-");
                startDate = new Date(Integer.parseInt(words[0])-1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );

                words = endDateString.split("-");
                endDate = new Date(Integer.parseInt(words[0])-1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2])  );
            }
            catch(Exception e)
            {
                startDate =null;
                endDate =null;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Initialising carb resource ends");
    }

    /**
     * The patient can store their data carb intake (measured in grams)
     * @param carbRepresentationIn  representation of a Carb given by the frontEnd
     */
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

    /**
     * Patients can view their average carb intake over a user- specified period
     * @return the average of carb intake per day as list
     * @throws NotFoundException if there are NO entries for the specified period
     */
    @Override
    public List<CarbRepresentation> getAverageCarbIntake() throws NotFoundException {
        LOGGER.finer("Select all carb entries for selected period.");

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        try {

            List<Carb> carbs;
            if (startDate ==null || endDate ==null)
                // find carbs within a range dates
                carbs = carbRepository.findAll();

            else
                carbs = carbRepository.findAverageCarbIntake(startDate , endDate);

            List<CarbRepresentation> result = new ArrayList<>();
            carbs.forEach(carb -> result.add(new CarbRepresentation(carb)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("Carb entries not found during selected period");
        }
    }
}
