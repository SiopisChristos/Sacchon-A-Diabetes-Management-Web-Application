import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient.component';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
];

@NgModule({
  declarations: [
    PatientComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes), 
    ReactiveFormsModule], 
  exports: [
    RouterModule, 
    PatientComponent]
})
export class PatientModule { }
