import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { PatientComponent } from './patient/patient/patient.component';
import { DoctorComponent } from './doctor/doctor/doctor.component';
import { TypeAccountComponent } from './login/type-account/type-account.component';
import { ChiefComponent } from './chief/chief/chief.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginFormComponent },
  { path: 'patient', component: PatientComponent },
  { path: 'doctor', component: DoctorComponent },
  { path: 'admin', component: ChiefComponent },
  { path: 'type', component: TypeAccountComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
