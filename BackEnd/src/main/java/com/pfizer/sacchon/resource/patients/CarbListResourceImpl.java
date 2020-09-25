package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.CarbRepository;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.resource.util.ResourceAuthorization;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarbListResourceImpl extends ServerResource implements CarbListResource {

    public static final Logger LOGGER = Engine.getLogger(CarbResourceImpl.class);

    private CarbRepository carbRepository;
    private EntityManager entityManager;
    private Date startDate;
    private Date endDate;
    private Date date;
    private PatientRepository patientRepository;
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
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
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
    public RepresentationResponse<Boolean> addCarbEntry(CarbRepresentation carbRepresentationIn){

        LOGGER.info("Add a new carb entry.");


        // Check entity
        //        ResourceValidator.notNull(productRepresentationIn);
        //        ResourceValidator.validate(productRepresentationIn);
        //        LOGGER.finer("Product checked");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        String username = ResourceAuthorization.currentUserToUsername();
        try {
            Patient p = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(username), this, this.LOGGER);

            // Converts CarbRepresentation to Carb
            Carb carbIn = carbRepresentationIn.createCarb(p);
            boolean result = carbRepository.save(carbIn);
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.info("Carb entry successfully added.");
            return new RepresentationResponse(200, Constants.CODE_200, result);
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
    public RepresentationResponse<Double> getAverageCarbIntake() throws NotFoundException {
        LOGGER.finer("Select all carb entries for selected period.");

        // Check authorization
//        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        String username = ResourceAuthorization.currentUserToUsername();
        try {

            Patient patient = EntityUtil.getFromOptionalEntity(patientRepository.findPatientByUsername(username),
                    this,LOGGER);

            Double carbsAvg;
//            if (startDate ==null || endDate ==null)
//                // find carbsAvg within a range dates
//                carbsAvg = carbRepository.findAll();
//            else
                carbsAvg = carbRepository.findAverageCarbIntake(patient, startDate , endDate);

            return new RepresentationResponse<>(200,Constants.CODE_200, carbsAvg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Carb entries not found during selected period");
        }
    }
}
