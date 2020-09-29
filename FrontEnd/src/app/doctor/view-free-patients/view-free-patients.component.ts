import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DoctorService } from '../doctor.service';
import { Patient } from 'src/app/patient/patient/patient';

@Component({
  selector: 'app-view-free-patients',
  templateUrl: './view-free-patients.component.html',
  styleUrls: ['./view-free-patients.component.scss'],
})
export class ViewFreePatientsComponent implements OnInit {
  constructor(private doctorService: DoctorService) {}
  listOfFreePatients: Patient[];

  ngOnInit(): void {
    this.doctorService.getFreePatients().subscribe((listFreePatients) => {
      this.listOfFreePatients = listFreePatients.data;
    });
  }

  selectPatient(id) {
    console.log(id);
    if (confirm('Are you sure?')) {
      this.doctorService.choosePatient(id).subscribe((result) => {
        if (result.data === true) 
          location.reload;
      });
    }
  }
}
