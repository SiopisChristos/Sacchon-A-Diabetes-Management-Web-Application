import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { PatientRegisterComponent } from './patient/patient-register/patient-register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { Routes } from '@angular/router';
import { CarbEntryComponent } from './carb/carb-entry/carb-entry.component';
import { CarbModule } from './carb/carb.module';
import { GlucoseModule } from './glucose/glucose.module';
import { PatientDeleteComponent } from './patient-delete/patient-delete.component';
import { PatientUpdateComponent } from './patient-update/patient-update.component';

const routes: Routes = [
];

@NgModule({
  declarations: [PatientComponent, PatientRegisterComponent, PatientDeleteComponent, PatientUpdateComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CarbModule,
    GlucoseModule
  ],
  exports: [
    PatientRegisterComponent,
    PatientComponent, PatientDeleteComponent, PatientUpdateComponent
  ]
})
export class PatientModule { }
