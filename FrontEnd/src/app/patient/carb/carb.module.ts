import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarbListComponent } from './carb-list/carb-list.component';
import { CarbEntryComponent } from './carb-entry/carb-entry.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CarbUpdateComponent } from './carb-update/carb-update.component';
import { CarbDeleteComponent } from './carb-delete/carb-delete.component';



@NgModule({
  declarations: [CarbListComponent, CarbEntryComponent, CarbUpdateComponent, CarbDeleteComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [
    CarbListComponent, CarbEntryComponent, CarbUpdateComponent, CarbDeleteComponent
  ]
})
export class CarbModule { }
