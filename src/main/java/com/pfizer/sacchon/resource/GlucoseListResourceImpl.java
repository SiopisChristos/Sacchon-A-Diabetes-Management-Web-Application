package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.repository.GlucoseRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class GlucoseListResourceImpl extends ServerResource implements GlucoseListResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private GlucoseRepository glucoseRepository;
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
     * Initializes the glucose repository
     */
    @Override
    protected void doInit() {
        LOGGER.info("Initialising glucose resource starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            glucoseRepository = new GlucoseRepository(entityManager);
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
        LOGGER.info("Initialising glucose resource ends");
    }

    /**
     * Patients can view their average daily blood glucose level over a user- specified period
     * @return the average of blood glucose level per day as list
     * @throws NotFoundException if there are NO entries for the specified period
     */
    @Override
    public List<GlucoseRepresentation> getAverageGlucoseLevel() throws NotFoundException {
        LOGGER.finer("Select all carb entries for selected period.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_USER);
        try {

            List<Glucose> glucoses;
            if (startDate ==null || endDate ==null)
                // find blood glucose within a range of dates
                glucoses = glucoseRepository.findAll();

            else
                glucoses = glucoseRepository.findAverageGlucoseLevel(startDate , endDate);

            List<GlucoseRepresentation> result = new ArrayList<>();
            glucoses.forEach(glucose -> result.add(new GlucoseRepresentation(glucose)));
            return result;
        } catch (Exception e) {
            throw new NotFoundException("Blood glucose entries not found during selected period");
        }
    }
}
