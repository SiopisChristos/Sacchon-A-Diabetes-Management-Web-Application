import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarbListComponent } from './carb-list/carb-list.component';
import { CarbEntryComponent } from './carb-entry/carb-entry.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [CarbListComponent, CarbEntryComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [
    CarbListComponent, CarbEntryComponent
  ]
})
export class CarbModule { }
