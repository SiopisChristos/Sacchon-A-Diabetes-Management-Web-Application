import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Carb } from '../carb/carb';
import { CarbService } from '../carb/carb.service';
import { Glucose } from '../glucose/glucose';
import { GlucoseService } from '../glucose/glucose.service';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-patient-my-data',
  templateUrl: './patient-my-data.component.html',
  styleUrls: ['./patient-my-data.component.scss'],
})
export class PatientMyDataComponent implements OnInit {
  viewAll: boolean;
  viewCarbs: boolean;

  constructor(private patientService: PatientService) {}
  listOfData1: Carb[];
  listOfData2: Glucose[];

  ngOnInit(): void {
    this.patientService.patientGetMyData().subscribe((viewData) => {
      this.listOfData1 = viewData.data[0];
      this.listOfData2 = viewData.data[1];
      this.viewAll = true;
      this.ngOnInit;
    });
  }

  view(type) {
    console.log(this.listOfData1);

    if (type == 'all') {
      this.viewAll = true;
    } else if (type == 'carbs') {
      this.viewCarbs = true;
      this.viewAll = false;
    } else {
      this.viewCarbs = false;
      this.viewAll = false;
    }
  }

  deleteCarb(id){
    if(confirm("You want to delete a Carb measurement!\nAre you sure?")){
      this.patientService.deleteCarbEntry(id).subscribe(glucoseData => {
        location.reload();
      });
    }
      return;
  }

  deleteGlucose(id){
    if(confirm("You want to delete a Glucose measurement!\nAre you sure?")){
      this.patientService.deleteGlucoseEntry(id).subscribe(glucoseData => {
        location.reload();
      });
    }
      return;
  }
}
