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
  private getTime(date?: Date) {
    return date != null ? new Date(date).getTime() : 0;
  }
  viewDiv: number = 0;
 

  constructor(private patientService: PatientService) {}
  listOfData1: Carb[];
  listOfData2: Glucose[];

  ngOnInit(): void {
    this.patientService.patientGetMyData().subscribe((viewData) => {
      this.listOfData1 = viewData.data[0];
      this.listOfData2 = viewData.data[1];

      this.listOfData1.sort((a: Carb, b: Carb)=>{
        return this.getTime(b.date) - this.getTime(a.date);
      });

      this.listOfData2.sort((a: Glucose, b: Glucose)=>{
        return this.getTime(b.dateTime) - this.getTime(a.dateTime);
      });
      
      this.ngOnInit;
    });
  }

  view(type) {
    
    if (type == 'all') {
      this.viewDiv = 0;
    } else if (type == 'carbs') {
      this.viewDiv = 1;
    } else {
      this.viewDiv = -1;

    }
  }

  deleteCarb(id) {
    if (confirm('You want to delete a Carb measurement!\nAre you sure?')) {
      this.patientService.deleteCarbEntry(id).subscribe((glucoseData) => {
        location.reload();
      });
    }
    return;
  }

  deleteGlucose(id) {
    if (confirm('You want to delete a Glucose measurement!\nAre you sure?')) {
      this.patientService.deleteGlucoseEntry(id).subscribe((glucoseData) => {
        location.reload();
      });
    }
    return;
  }
}
