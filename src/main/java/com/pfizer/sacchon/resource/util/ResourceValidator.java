package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Note;


import java.util.Optional;
import java.util.logging.Logger;

public class ResourceValidator {

    public static void checkNoteIntegrity(Note oldNote, Note noteIn) throws BadEntityException {
        if (!oldNote.getDoctor().equals(noteIn.getDoctor()))
            throw new BadEntityException("Can't change doctor_id in your Note");
        if (!oldNote.getPatient().equals(noteIn.getPatient()))
            throw new BadEntityException("Can't change patient_id in your Note");
    }


}
