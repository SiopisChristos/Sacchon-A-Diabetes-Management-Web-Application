import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorService } from '../doctor.service';
import { DoctorComponent } from '../doctorComponent/doctor.component';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports: [
    DoctorComponent
  ]
})
export class DoctorModuleModule { }
