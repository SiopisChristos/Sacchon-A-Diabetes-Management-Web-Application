import { Component, OnInit } from '@angular/core';
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

  ngOnInit(): void {
    this.doctorService.getMyPatients().subscribe(
      listOfMyPatients => {this.listOfMyPatients = listOfMyPatients;
  })
}

}
