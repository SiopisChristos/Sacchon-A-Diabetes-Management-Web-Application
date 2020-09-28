import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-doctor-register',
  templateUrl: './doctor-register.component.html',
  styleUrls: ['./doctor-register.component.scss']
})
export class DoctorRegisterComponent implements OnInit {

  formDoctorRegister: FormGroup
  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
    this.formDoctorRegister = new FormGroup({
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      dateOfBirth: new FormControl(null, Validators.required),
      phoneNumber: new FormControl(null, Validators.required),
      specialty: new FormControl(null, Validators.required),
    })
  }
  clickRegisterDoctor() {
    this.doctorService.registerDoctor(this.formDoctorRegister).subscribe(registerData => {
      alert(JSON.stringify(registerData));
      this.ngOnInit;
    })
  }
  RegisterUser() {
    this.doctorService.UserRegistration(this.formDoctorRegister).subscribe(registerUserData => {
      alert(JSON.stringify(registerUserData));
      this.ngOnInit;

    })
  }
}