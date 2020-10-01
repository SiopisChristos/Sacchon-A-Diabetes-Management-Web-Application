import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { strict } from 'assert';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-entry',
  templateUrl: './note-entry.component.html',
  styleUrls: ['./note-entry.component.scss']
})
export class NoteEntryComponent implements OnInit {

  formNoteEntry: FormGroup;
  patient_id: number;
  result:number;
  constructor(private noteService: NoteService, private route: ActivatedRoute) { }

  
  ngOnInit(): void {
    this.formNoteEntry = new FormGroup({
      message: new FormControl(null, Validators.required),
      date: new FormControl(null, Validators.required)
    })
    this.route.params.subscribe(params => {
      this.patient_id = params.id; 
    }) 
  }

  clickNoteEntrySubmit(){
    this.noteService.addNoteEntry(this.formNoteEntry, this.patient_id).subscribe(noteData => {
      alert(JSON.stringify(noteData));
      this.result = noteData.status;
      if(this.result == 200 || this.result == 204)
        this.result = 1;
      else
        this.result=-1
      this.ngOnInit;
    })
  }
}
