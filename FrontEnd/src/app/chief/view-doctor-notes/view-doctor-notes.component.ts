import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Doctor } from 'src/app/doctor/doctor';
import { Note } from 'src/app/doctor/note/note';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-doctor-notes',
  templateUrl: './view-doctor-notes.component.html',
  styleUrls: ['./view-doctor-notes.component.scss']
})
export class ViewDoctorNotesComponent implements OnInit {

  constructor(private chiefService: ChiefService ) { }
  formViewDoctorNotes: FormGroup;
  listOfNotes: Note[];

  ngOnInit(): void {
    this.formViewDoctorNotes = new FormGroup({
      id: new FormControl(null, Validators.required),
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  
  clickViewDoctorNotes(){
    this.chiefService.getDoctorNotes(this.formViewDoctorNotes).subscribe(viewDoctorNotesData => {
      this.listOfNotes = viewDoctorNotesData.data;
      this.ngOnInit;
  })
}

}