package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.repository.DoctorRepository;
import com.pfizer.sacchon.repository.util.JpaUtil;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorResourceImpl extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository;
    private long id;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");

        try {
            doctorRepository =
                    new DoctorRepository (JpaUtil.getEntityManager()) ;
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception e)
        {
            id =-1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }


    @Override
    public DoctorRepresentation getDoctor() throws NotFoundException {

        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        Doctor doctor;
        try {

            Optional<Doctor> thedoctor = doctorRepository.findDoctorById(id);

            setExisting(thedoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor id does not exist:" + id);
                throw new NotFoundException("No doctor with  : " + id);
            } else {
                doctor = thedoctor.get();
                LOGGER.finer("User allowed to retrieve a doctor");
                DoctorRepresentation result =
                        new DoctorRepresentation(doctor);

                LOGGER.finer("Doctor successfully retrieved");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }


    @Override
    public boolean removeDoctor() throws NotFoundException {
            LOGGER.finer("delete doctor");

        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());

            try {
                Boolean isDeleted = doctorRepository.removeDoctor(id);

            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when removing a product", ex);
                throw new ResourceException(ex);
            }
        return false;
    }

    }
