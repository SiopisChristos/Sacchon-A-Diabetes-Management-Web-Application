package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.*;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;

import java.lang.reflect.Parameter;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity The entity to check.
     * @throws BadEntityException In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null) {
            throw new BadEntityException("No input entity");
        }
    }

    public static void checkNotePatient(Note oldNote, Patient loggedInPatient) throws NotAuthorizedException{
        if (!oldNote.getPatient().equals(loggedInPatient))
            throw new NotAuthorizedException("Not your note!");
    }

    public static void checkNoteDoctor(Note oldNote, Doctor loggedInDoctor) throws NotAuthorizedException{
        if (!oldNote.getDoctor().equals(loggedInDoctor))
            throw new NotAuthorizedException("Not your note");
    }


    public static void checkCarbIntegrity(Carb oldCarb, Carb carbIn) throws NotAuthorizedException {
        if (!oldCarb.getDate().equals(carbIn.getDate()))
            throw new NotAuthorizedException("Can't change date");
    }

    public static void checkGlucoseIntegrity(Glucose oldGlucose, Patient patient) throws NotAuthorizedException {
        if (!oldGlucose.getPatient().equals(patient))
            throw new NotAuthorizedException("Can't change date");
    }

    /**
     * Some checks to validate data of patient
     *
     * @param patientRepresentation
     * @throws BadEntityException
     */
    public static void validate(PatientRepresentation patientRepresentation)
            throws BadEntityException {
//        if ( patientRepresentation.getFirstName()==null) {
//            throw new BadEntityException(
//                    "patient name cannot be null");
//        }
//        if ( patientRepresentation.getLastName()==null) {
//            throw new BadEntityException(
//                    "patient name cannot be null");
//        }
//        if ( patientRepresentation.getPhoneNumber().length()!=10 && patientRepresentation.getPhoneNumber()!=null) {
//            throw new BadEntityException(
//                    "patient invalid phone number");
//        }
//        if ( patientRepresentation.getUsername()==null) {
//            throw new BadEntityException(
//                    "patient username cannot be null");
//        }
//        if ( patientRepresentation.getAddress()==null) {
//            throw new BadEntityException(
//                    "patient address cannot be null");
//        }
//        if ( patientRepresentation.getCity()==null) {
//            throw new BadEntityException(
//                    "patient city cannot be null");
//        }
//
    }

}
