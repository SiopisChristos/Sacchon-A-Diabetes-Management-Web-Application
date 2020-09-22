import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlucoseListComponent } from './glucose-list/glucose-list.component';
import { GlucoseEntryComponent } from './glucose-entry/glucose-entry.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [GlucoseListComponent, GlucoseEntryComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [
    GlucoseEntryComponent, GlucoseListComponent
  ]
})
export class GlucoseModule { }
