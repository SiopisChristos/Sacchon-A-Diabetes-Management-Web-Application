import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewFreePatientsComponent } from './view-free-patients/view-free-patients.component';
import { ViewPatientDataComponent } from './view-patient-data/view-patient-data.component';
import { ViewDoctorNotesComponent } from './view-doctor-notes/view-doctor-notes.component';
import { ViewInactivePatientsComponent } from './view-inactive-patients/view-inactive-patients.component';
import { ViewInactiveDoctorsComponent } from './view-inactive-doctors/view-inactive-doctors.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [ViewFreePatientsComponent, ViewPatientDataComponent, ViewDoctorNotesComponent, ViewInactivePatientsComponent, ViewInactiveDoctorsComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [ViewDoctorNotesComponent, ViewFreePatientsComponent, ViewInactiveDoctorsComponent, ViewInactivePatientsComponent,ViewPatientDataComponent]
})
export class ChiefModule { }
