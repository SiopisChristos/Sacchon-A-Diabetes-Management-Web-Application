import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Carb } from '../carb/carb';
import { Glucose } from '../glucose/glucose';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-patient-my-data',
  templateUrl: './patient-my-data.component.html',
  styleUrls: ['./patient-my-data.component.scss']
})
export class PatientMyDataComponent implements OnInit {

  constructor(private patientService: PatientService) { }
  listOfData1: Carb[];
  listOfData2: Glucose[];

  ngOnInit(): void {
    this.patientService.patientGetMyData().subscribe(viewData => {
      this.listOfData1 = viewData.data[0];
      this.listOfData2 = viewData.data[1];
      this.ngOnInit;
    })
  }

}
