import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { DoctorComponent } from './doctor.component';

const routes: Routes = [];

@NgModule({
  declarations: [DoctorComponent],
  imports: [
    RouterModule.forRoot(routes), ReactiveFormsModule
  ],
  exports: [
    RouterModule, DoctorComponent
  ]
})
export class DoctorModule { }
