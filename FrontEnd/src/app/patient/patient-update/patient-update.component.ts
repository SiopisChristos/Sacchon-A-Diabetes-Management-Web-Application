import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrls: ['./patient-update.component.scss']
})
export class PatientUpdateComponent implements OnInit {

  formPatientUpdate: FormGroup
  constructor(private patientService: PatientService) { }

  ngOnInit(): void {
    this.formPatientUpdate = new FormGroup({
      id: new FormControl(null),
      firstName: new FormControl(null),
      lastName: new FormControl(null),
      dateOfBirth: new FormControl(null),
      city: new FormControl(null),
      phoneNumber: new FormControl(null,[Validators.minLength(10),Validators.maxLength(9)]),
      address: new FormControl(null)
    })
  }
  clickPatientUpdateSubmit() {
    this.patientService.patientUpdate(this.formPatientUpdate).subscribe(patientUpdateData => {
      alert(JSON.stringify(patientUpdateData));
      this.ngOnInit;
    })

  }

}
