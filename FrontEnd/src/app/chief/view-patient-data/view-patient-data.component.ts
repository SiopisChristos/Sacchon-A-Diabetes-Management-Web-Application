import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Patient } from 'src/app/patient/patient/patient';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';

@Component({
  selector: 'app-view-patient-data',
  templateUrl: './view-patient-data.component.html',
  styleUrls: ['./view-patient-data.component.scss']
})
export class ViewPatientDataComponent implements OnInit {
  id:number;
  listOfCarbs: Carb[];
  listOfGlucose: Glucose[];
  constructor(private chiefService: ChiefService, private route: ActivatedRoute) { }
  formViewPatientData: FormGroup;
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.id = params.id;   
  });

    this.formViewPatientData = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewPatientData(){
    this.chiefService.getPatientData(this.formViewPatientData,this.id).subscribe(viewPatientData => {

      this.listOfCarbs = viewPatientData.data[0];
      this.listOfGlucose = viewPatientData.data[1];
      this.ngOnInit;
  })
}

}