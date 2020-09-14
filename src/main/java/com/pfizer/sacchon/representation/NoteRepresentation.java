package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NoteRepresentation {

    private String message;
    private Date date;

    public NoteRepresentation(Note note) {
        if (note != null) {
            message = note.getMessage();
            date = note.getDate();
        }
    }
}

