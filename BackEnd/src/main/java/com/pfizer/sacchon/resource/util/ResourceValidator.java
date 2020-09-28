package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.*;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.UserRegistrationRepr;

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

    public static void checkNotePatient(Note oldNote, String usernameLoggedIn) throws NotAuthorizedException{
        if (!oldNote.getPatient().getUsername().equals(usernameLoggedIn))
            throw new NotAuthorizedException("Not your note!");
    }

    public static void checkNoteDoctor(Note oldNote, String usernameLoggedIn) throws NotAuthorizedException{
        if (!oldNote.getDoctor().getUsername().equals(usernameLoggedIn))
            throw new NotAuthorizedException("Not your note");
    }


    public static void checkCarbIntegrity(Carb oldCarb, String usernameLoggedIn) throws NotAuthorizedException {
        if (!oldCarb.getPatient().getUsername().equals(usernameLoggedIn))
            throw new NotAuthorizedException("Can't change date");
    }

    public static void checkGlucoseIntegrity(Glucose oldGlucose, String usernameLoggedIn) throws NotAuthorizedException {
        if (!oldGlucose.getPatient().getUsername().equals(usernameLoggedIn))
            throw new NotAuthorizedException("Can't change date");
    }

    /**
     * Some checks to validate data of patient
     *
     * @param patientRepresentation
     * @throws BadEntityException
     */
    public static void validatePatient(PatientRepresentation patientRepresentation)
            throws BadEntityException {
        if ( patientRepresentation.getFirstName()==null) {
            throw new BadEntityException(
                    "patient name cannot be null");
        }
        if ( patientRepresentation.getLastName()==null) {
            throw new BadEntityException(
                    "patient name cannot be null");
        }
        if ( patientRepresentation.getPhoneNumber().length()!=10 && patientRepresentation.getPhoneNumber()!=null) {
            throw new BadEntityException(
                    "patient invalid phone number");
        }
        if ( patientRepresentation.getUsername()==null) {
            throw new BadEntityException(
                    "patient username cannot be null");
        }
        if ( patientRepresentation.getAddress()==null) {
            throw new BadEntityException(
                    "patient address cannot be null");
        }
        if ( patientRepresentation.getCity()==null) {
            throw new BadEntityException(
                    "patient city cannot be null");
        }

    }

    public static void validateDoctor(DoctorRepresentation doctorRepresentation)
            throws BadEntityException {
        if ( doctorRepresentation.getFirstName()==null) {
            throw new BadEntityException(
                    "name cannot be null");
        }
        if ( doctorRepresentation.getLastName()==null) {
            throw new BadEntityException(
                    "name cannot be null");
        }
        if ( doctorRepresentation.getPhoneNumber().length()!=10 && doctorRepresentation.getPhoneNumber()!=null) {
            throw new BadEntityException(
                    "invalid phone number");
        }
        if ( doctorRepresentation.getUsername()==null) {
            throw new BadEntityException(
                    "username cannot be null");
        }
        if ( doctorRepresentation.getSpecialty()==null) {
            throw new BadEntityException(
                    "specialty cannot be null");
        }
        if ( doctorRepresentation.getDateOfBirth()==null) {
            throw new BadEntityException(
                    "date of birth city cannot be null");
        }

    }

    public static void validateUserRegister(UserRegistrationRepr userRegistrationRepr) throws BadEntityException{
        if (userRegistrationRepr.getUsername() != null)
            throw new BadEntityException("Username cannot be null");
        if (userRegistrationRepr.getPassword() != null)
            throw new BadEntityException("Password cannot br null");
    }

}
