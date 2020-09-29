import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DoctorService } from '../doctor.service';
import { Patient } from 'src/app/patient/patient/patient';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';

@Component({
  selector: 'app-view-free-patients',
  templateUrl: './view-free-patients.component.html',
  styleUrls: ['./view-free-patients.component.scss'],
})
export class ViewFreePatientsComponent implements OnInit {
  constructor(private doctorService: DoctorService) {}
  listOfFreePatients: Patient[];
  listOfData1: Carb[];
  listOfData2: Glucose[];

  ngOnInit(): void {
    this.doctorService.getFreePatients().subscribe((listFreePatients) => {
      this.listOfFreePatients = listFreePatients.data;
    });
  }

  clickGetPatientsData(patient_id){
    this.doctorService.getPatientsData(patient_id).subscribe(viewData => {
        this.listOfData1 = viewData.data[0];
        this.listOfData2 = viewData.data[1];
        this.ngOnInit;
    })
  }

  selectPatient(patient_id) {
    alert(patient_id);
    if (confirm('Are you sure?')) {
      this.doctorService.choosePatient(patient_id).subscribe((result) => {
        console.log(result);
        
        if (result.data === true)          
          location.reload();
        
      });
    }
  }
}
