import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-entry',
  templateUrl: './note-entry.component.html',
  styleUrls: ['./note-entry.component.scss']
})
export class NoteEntryComponent implements OnInit {

  formNoteEntry: FormGroup;
  
  constructor(private NoteService: NoteService) { }

  ngOnInit(): void {
    this.formNoteEntry = new FormGroup({
      message: new FormControl(null, Validators.required),
      date: new FormControl(null, Validators.required)
    })
  }

  clickNoteEntrySubmit(){
    this.NoteService.addNoteEntry(this.formNoteEntry).subscribe(noteData => {
      alert(JSON.stringify(noteData));
      this.ngOnInit;
    })
  }
}
