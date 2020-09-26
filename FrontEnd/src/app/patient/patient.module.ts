import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { PatientRegisterComponent } from './patient/patient-register/patient-register.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [PatientComponent, PatientRegisterComponent],
  imports: [
    CommonModule,ReactiveFormsModule
  ],
  exports:[PatientRegisterComponent]
})
export class PatientModule { }
