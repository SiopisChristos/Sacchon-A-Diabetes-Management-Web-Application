package com.pfizer.sacchon.representation;

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
    private long patient_id;
    private long doctor_id;
    private boolean seen;
    private String patientLastname;
    private String doctorLastname;

    public NoteRepresentation(Note note) {
        if (note != null) {
            message = note.getMessage();
            date = note.getDate();
            patient_id = note.getPatient().getId();
            doctor_id = note.getDoctor().getId();
            patientLastname = note.getPatient().getLastName();
            doctorLastname = note.getDoctor().getLastName();
        }
    }

    public Note createNote(Patient patient, Doctor doctor) {
        Note note = new Note();
        note.setDoctor(doctor);
        note.setPatient(patient);
        note.setMessage(message);
        note.setDate(date);
        return note;
    }
}

