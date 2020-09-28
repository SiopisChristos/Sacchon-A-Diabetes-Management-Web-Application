import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-register',
  templateUrl: './patient-register.component.html',
  styleUrls: ['./patient-register.component.scss']
})
export class PatientRegisterComponent implements OnInit {


  formPatientRegister: FormGroup

  constructor(private patientService: PatientService) { }

  ngOnInit(): void {
    this.formPatientRegister = new FormGroup({
      username: new FormControl(null, Validators.required),
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      dateOfBirth: new FormControl(Validators.required),
      city: new FormControl(null, Validators.required),
      phoneNumber: new FormControl(null, [Validators.required, Validators.minLength(10)]),
      address: new FormControl(null, Validators.required),
      zipCode: new FormControl(null, Validators.required),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)])
    })
  }

  clickRegisterPatient() {
    this.RegisterPatient();
    this.RegisterUser();
    this.ngOnInit;
  }
  RegisterPatient() {
    this.patientService.registerPatient(this.formPatientRegister).subscribe(registerData => {
      alert(JSON.stringify(registerData));



    })
  }
  RegisterUser() {
    this.patientService.UserRegistration(this.formPatientRegister).subscribe(registerUserData => {
      alert(JSON.stringify(registerUserData));

    })
  }
}
