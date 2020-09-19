package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity
     *            The entity to check.
     * @throws BadEntityException
     *             In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null) {
            throw new BadEntityException("No input entity");
        }
    }

    /**
     * d.
     *
     * @param patientRepresentation
     * @throws BadEntityException
     */
    public static void validate(PatientRepresentation patientRepresentation)
            throws BadEntityException {
        if ( patientRepresentation.getFirstName()==null) {
            throw new BadEntityException(
                    "patient name cannot be null");
        }
    }

}
