import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';
import { Patient } from 'src/app/patient/patient/patient';
import { PatientService } from 'src/app/patient/patient/patient.service';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-see-patients-data',
  templateUrl: './see-patients-data.component.html',
  styleUrls: ['./see-patients-data.component.scss']
})
export class SeePatientsDataComponent implements OnInit {

  constructor(private doctorService: DoctorService) { }
  listOfData1: Carb[];
  listOfData2: Glucose[];

  ngOnInit(): void {
    // this.doctorService.getPatientsData(this.patient_id).subscribe(viewData => {
    //   this.listOfData1 = viewData.data[0];
    //   this.listOfData2 = viewData.data[1];
    //   this.ngOnInit;
    // })
  }
}
