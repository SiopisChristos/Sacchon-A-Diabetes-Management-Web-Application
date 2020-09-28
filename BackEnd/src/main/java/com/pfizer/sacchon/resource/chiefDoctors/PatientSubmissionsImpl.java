package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.model.Carb;

import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.repository.ChiefRepository;
import com.pfizer.sacchon.repository.PatientRepository;
import com.pfizer.sacchon.repository.util.EntityUtil;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import com.pfizer.sacchon.resource.constant.Constants;
import com.pfizer.sacchon.security.ResourceUtils;
import com.pfizer.sacchon.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PatientSubmissionsImpl extends ServerResource implements PatientSubmissions {

    public static final Logger LOGGER = Engine.getLogger(PatientSubmissionsImpl.class);
    private ChiefRepository chiefRepository;
    private PatientRepository patientRepository;
    private EntityManager entityManager;
    private Date startDate;
    private Date endDate;
    private long id;

    @Override
    protected void doRelease() {
        entityManager.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initializing patientsNoActivity starts");
        try {
            entityManager = JpaUtil.getEntityManager();
            chiefRepository = new ChiefRepository(entityManager);
            patientRepository = new PatientRepository(entityManager);
            id =  Long.parseLong(getAttribute("id"));
            try {
                String startDateString = getQueryValue("from");
                String endDateString = getQueryValue("to");
                String[] words = startDateString.split("-");

                startDate = new Date(Integer.parseInt(words[0]) - 1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2]));

                words = endDateString.split("-");
                endDate = new Date(Integer.parseInt(words[0]) - 1900,
                        Integer.parseInt(words[1]) - 1, Integer.parseInt(words[2]));

                System.out.println("The Dates are: " + startDate + "\n" + endDate);

            } catch (Exception e) {
                startDate = null;
                endDate = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initializing doctorsNoActivity ends");
    }

    @Override
    public RepresentationResponse<List> patientData() {

        try {
            ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
            if (startDate == null || endDate == null)
                throw new BadEntityException("The input dates are not valid");

            Patient patient = EntityUtil.getFromOptionalEntity(patientRepository.findById(id),this,LOGGER);

            List<Carb> carbList = chiefRepository.findCarbs(patient, startDate, endDate);
            List<CarbRepresentation> carbRepresentation = new ArrayList<>();
            carbList.forEach(x -> carbRepresentation.add(new CarbRepresentation(x)));


            List<Glucose> glucoseList = chiefRepository.findGlucose(patient, startDate, endDate);
            List<GlucoseRepresentation> glucoseRepresentation = new ArrayList<>();
            glucoseList.forEach(x -> glucoseRepresentation.add(new GlucoseRepresentation(x)));


            List[] patientData = {carbRepresentation, glucoseRepresentation};

            return new RepresentationResponse(200, Constants.CODE_200, patientData);
        } catch (
                ResourceException e) {
            e.printStackTrace();
            return new RepresentationResponse(403, Constants.CODE_403, Constants.RESPONSE_403);
        } catch (
                BadEntityException e) {
            e.printStackTrace();
            return new RepresentationResponse(400, Constants.CODE_400, Constants.RESPONSE_400);
        } catch (
                Exception e) {
            e.printStackTrace();
            return new RepresentationResponse(500, Constants.CODE_500, Constants.RESPONSE_500);
        }
    }

}
