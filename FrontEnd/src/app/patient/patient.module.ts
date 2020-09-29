import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { PatientRegisterComponent } from './patient/patient-register/patient-register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { CarbEntryComponent } from './carb/carb-entry/carb-entry.component';
import { CarbModule } from './carb/carb.module';
import { GlucoseModule } from './glucose/glucose.module';
import { PatientUpdateComponent } from './patient-update/patient-update.component';
import { PatientDeleteComponent } from './patient-delete/patient-delete.component';
import { PatientMyDataComponent } from './patient-my-data/patient-my-data.component';
import { NotesComponent } from './notes/notes.component';

const routes: Routes = [
  { path: 'deletePatient', component: PatientDeleteComponent },
  { path: 'updatePatient', component: PatientUpdateComponent },
  {path: 'registerPatient', component:PatientRegisterComponent},
  {path: 'seeMydata',component:PatientMyDataComponent},
  {path: 'myNotes', component: NotesComponent}
];

@NgModule({
  declarations: [PatientComponent, PatientRegisterComponent, PatientDeleteComponent, PatientUpdateComponent, PatientMyDataComponent, NotesComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CarbModule,
    GlucoseModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    PatientRegisterComponent,
    PatientComponent, PatientDeleteComponent, PatientUpdateComponent,PatientMyDataComponent, NotesComponent
  ]
})
export class PatientModule { }
