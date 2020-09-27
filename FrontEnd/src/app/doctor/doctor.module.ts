import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteEntryComponent } from './note/note-entry/note-entry.component';
import { NoteUpdateComponent } from './note/note-update/note-update.component';
import { DoctorComponent } from './doctor/doctor.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NoteModule } from './note/note.module';
import { Route } from '@angular/compiler/src/core';
import { RouterModule, Routes } from '@angular/router';
import { DoctorRegisterComponent } from './doctor-register/doctor-register.component';
import { DoctorDeleteComponent } from './doctor-delete/doctor-delete.component';
import { PatientRegisterComponent } from '../patient/patient/patient-register/patient-register.component';


const routes: Routes = [
  { path: 'insertNote', component: NoteEntryComponent },
  { path: 'updateNote', component: NoteUpdateComponent },
  // {path: 'updateCarbEntry',component: },
  // {path: 'deleteCarbEntry',  component: CarbDeleteComponent},
  { path: 'registerPatient', component: PatientRegisterComponent },
  { path: 'registerDoctor', component: DoctorRegisterComponent },
  //{path: 'seeFreePatients',component:SeeFreePatientsComponent},
  { path: 'deleteDoctor', component: DoctorDeleteComponent },
]


@NgModule({
  declarations: [DoctorComponent, DoctorRegisterComponent, DoctorDeleteComponent,],
  imports: [CommonModule, ReactiveFormsModule, NoteModule, RouterModule.forRoot(routes)],
  exports: [DoctorComponent, DoctorRegisterComponent, DoctorDeleteComponent],
})
export class DoctorModule { }
