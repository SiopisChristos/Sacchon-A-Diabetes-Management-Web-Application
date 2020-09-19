package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.repository.CarbRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class CarbListResourceImpl extends ServerResource implements CarbListResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private CarbRepository carbRepository;
    private EntityManager entityManager;
    private Date startDate;
    private Date endDate;
    private Long id;

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
     * Patients can view their average daily blood glucose level over a user- specified period
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
