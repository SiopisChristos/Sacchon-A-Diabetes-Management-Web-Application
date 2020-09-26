import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { CarbEntryComponent } from '../patient/carb/carb-entry/carb-entry.component';
import { PatientComponent } from '../patient/patient/patient.component';

const routes: Routes = [
  {path:'', redirectTo: 'login', pathMatch:'full'},
  {path:'login',component: LoginFormComponent},
  {path:'patient', component: PatientComponent},
  // {path:'doctor', component: DoctorComponent},
];

@NgModule({
  declarations: [LoginFormComponent],
  imports: [
    RouterModule,
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    LoginFormComponent,
    RouterModule
  ]
})
export class LoginModule { }
