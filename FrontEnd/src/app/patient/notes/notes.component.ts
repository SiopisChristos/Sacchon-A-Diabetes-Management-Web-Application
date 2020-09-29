import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Note } from 'src/app/doctor/note/note';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.scss']
})
export class NotesComponent implements OnInit {

  constructor(private patientService: PatientService) { }

  listOfNotes: Note[];

  ngOnInit(): void {
    this.patientService.getMyNotes().subscribe(viewMyNotes => {
      this.listOfNotes = viewMyNotes;
      console.log(viewMyNotes);
    })
  }
 
  clickViewMyNotes(){
    this.patientService.getMyNotes().subscribe(viewMyNotes => {
      this.listOfNotes = viewMyNotes;
      
      
      this.ngOnInit;
  })
}

}