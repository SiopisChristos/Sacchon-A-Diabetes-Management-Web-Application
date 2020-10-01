import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-update',
  templateUrl: './note-update.component.html',
  styleUrls: ['./note-update.component.scss']
})
export class NoteUpdateComponent implements OnInit {

  resultDiv:number;
  formNoteUpdate: FormGroup;
  constructor(private noteService: NoteService ) { }

  ngOnInit(): void {
    this.formNoteUpdate = new FormGroup({
      id: new FormControl(null, Validators.required),
      message: new FormControl(null, Validators.required)
  })
}

clickNoteUpdateSubmit(){
  this.noteService.updateNoteEntry(this.formNoteUpdate).subscribe(noteUpdateData => {
    this.resultDiv = (noteUpdateData.status == 204) ? 1 : -1;
    this.ngOnInit;
  })
}

}

