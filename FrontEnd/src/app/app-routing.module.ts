import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { NoteEntryComponent } from './doctor/note/note-entry/note-entry.component';
import { NoteUpdateComponent } from './doctor/note/note-update/note-update.component';
import { CarbDeleteComponent } from './patient/carb/carb-delete/carb-delete.component';
import { CarbEntryComponent } from './patient/carb/carb-entry/carb-entry.component';
import { PatientComponent } from './patient/patient/patient.component';
import { DoctorComponent } from './doctor/doctor/doctor.component';
import { PatientUpdateComponent } from './patient/patient-update/patient-update.component';
import { TypeAccountComponent } from './login/type-account/type-account.component';
import { ChiefComponent } from './chief/chief/chief.component';

const routes: Routes = [
  {path:'', redirectTo: 'login', pathMatch:'full'},
  {path:'login',component: LoginFormComponent},
  {path:'patient',component: PatientComponent},
  {path:'doctor',component: DoctorComponent},
  {path: 'admin', component: ChiefComponent},
  {path:'type',component:TypeAccountComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
