import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-doctor-notes',
  templateUrl: './view-doctor-notes.component.html',
  styleUrls: ['./view-doctor-notes.component.scss']
})
export class ViewDoctorNotesComponent implements OnInit {

  constructor(private chiefService: ChiefService ) { }

  formViewDoctorNotes: FormGroup;

  ngOnInit(): void {
    this.formViewDoctorNotes = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  
  clickViewDoctorNotes(){
    this.chiefService.getDoctorNotes(this.formViewDoctorNotes).subscribe(viewDoctorNotesData => {
      alert(JSON.stringify(viewDoctorNotesData));
      this.ngOnInit;
  })
}

}