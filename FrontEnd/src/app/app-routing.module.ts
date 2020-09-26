import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { CarbEntryComponent } from './patient/carb/carb-entry/carb-entry.component';
import { PatientComponent } from './patient/patient/patient.component';

const routes: Routes = [
  {path:'', redirectTo: 'login', pathMatch:'full'},
  {path:'login',component: LoginFormComponent},
  {path:'patient',component: PatientComponent},
  // {path:'doctor',component: DoctorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
