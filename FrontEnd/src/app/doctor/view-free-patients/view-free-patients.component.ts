import { Component, Input, OnInit } from '@angular/core';
import { DoctorService } from '../doctor.service';
import { Patient } from 'src/app/patient/patient/patient';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';
import { Note } from '../note/note';

@Component({
  selector: 'app-view-free-patients',
  templateUrl: './view-free-patients.component.html',
  styleUrls: ['./view-free-patients.component.scss'],
})
export class ViewFreePatientsComponent implements OnInit {
  
  private getTime(date?: Date) {
    return date != null ? new Date(date).getTime() : 0;
}

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

        this.listOfData1.sort((a: Carb, b: Carb)=>{
          return this.getTime(b.date) - this.getTime(a.date);
        });
  
        this.listOfData2.sort((a: Glucose, b: Glucose)=>{
          return this.getTime(b.dateTime) - this.getTime(a.dateTime);
        });
        
        this.ngOnInit;
    })
  }

  selectPatient(patient_id) {
    if (confirm('Are you sure?')) {
      this.doctorService.choosePatient(patient_id).subscribe((result) => {
        console.log(result);
        
        if (result.data == true)          
          location.reload();
        else
        {
          alert("The Patient needs more time to be consulted!");
        }
      });
    }
  }
}
