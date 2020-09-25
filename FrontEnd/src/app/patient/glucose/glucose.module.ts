import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlucoseListComponent } from './glucose-list/glucose-list.component';
import { GlucoseEntryComponent } from './glucose-entry/glucose-entry.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GlucoseUpdateComponent } from './glucose-update/glucose-update.component';
import { GlucoseDeleteComponent } from './glucose-delete/glucose-delete.component';



@NgModule({
  declarations: [GlucoseListComponent, GlucoseEntryComponent, GlucoseUpdateComponent, GlucoseDeleteComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [
    GlucoseEntryComponent, GlucoseListComponent, GlucoseDeleteComponent, GlucoseUpdateComponent
  ]
})
export class GlucoseModule { }
