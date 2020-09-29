import { Component, OnInit } from '@angular/core';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';
import { Patient } from 'src/app/patient/patient/patient';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-see-my-patients',
  templateUrl: './see-my-patients.component.html',
  styleUrls: ['./see-my-patients.component.scss']
})
export class SeeMyPatientsComponent implements OnInit {

  constructor(private doctorService: DoctorService) { }
  listOfMyPatients: Patient[];
  data1: Carb[];
  data2: Glucose[];
  ngOnInit(): void {
    this.doctorService.getMyPatients().subscribe(
      listMypatient => { this.listOfMyPatients = listMypatient.data; }
    )
    this.seedata();

    this.ngOnInit;
  }
  seedata() {
    this.doctorService.getPatientsData().subscribe(lis => {
      this.data1 = lis.data[0];
      this.data2 = lis.data[1];
    });
  }

}
