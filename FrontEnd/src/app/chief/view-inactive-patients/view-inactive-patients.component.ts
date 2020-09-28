import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Patient } from 'src/app/patient/patient/patient';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-inactive-patients',
  templateUrl: './view-inactive-patients.component.html',
  styleUrls: ['./view-inactive-patients.component.scss']
})
export class ViewInactivePatientsComponent implements OnInit {

  constructor(private chiefService: ChiefService) { }

  listOfPatients: Patient[];
  formViewInactivePatients: FormGroup;

  ngOnInit(): void {

    this.formViewInactivePatients = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewInactivePatients(){
    this.chiefService.getInactivePatients(this.formViewInactivePatients).subscribe(viewInactivePatients => {
      this.listOfPatients = viewInactivePatients.data[0];
      this.ngOnInit;
  })
}

}