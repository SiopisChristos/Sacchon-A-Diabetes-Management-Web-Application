package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Note;
import com.pfizer.sacchon.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NoteRepresentation {

    private String message;
    private Date date;
    private Patient patient;
    private Doctor doctor;
    private String uri;

    public NoteRepresentation(Note note) {
        if (note != null) {
            message = note.getMessage();
            date = note.getDate();
            patient = note.getPatient();
            doctor = note.getDoctor();
            uri = "http://localhost:9000/v1/patient/note/" + note.getId();
        }
    }

    public Note createNote() {
        Note note = new Note();
        note.setMessage(message);
        note.setDate(date);
        note.setPatient(patient);
        note.setDoctor(doctor);
        return note;
    }
}

