import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlucoseListComponent } from './glucose-list/glucose-list.component';
import { GlucoseEntryComponent } from './glucose-entry/glucose-entry.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GlucoseUpdateComponent } from './glucose-update/glucose-update.component';
import { GlucoseDeleteComponent } from './glucose-delete/glucose-delete.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: 'insertGlucoseEntry',  component: GlucoseEntryComponent},
  {path: 'viewAverageGlucoseIntake',component: GlucoseListComponent},
  {path: 'updateGlucoseEntry',component: GlucoseUpdateComponent},
  {path: 'deleteGlucoseEntry',  component: GlucoseDeleteComponent},
]

@NgModule({
  declarations: [
    GlucoseListComponent, 
    GlucoseEntryComponent, 
    GlucoseUpdateComponent, 
    GlucoseDeleteComponent
  ],
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule,
    GlucoseEntryComponent,
    GlucoseListComponent,
    GlucoseDeleteComponent,
    GlucoseUpdateComponent
  ]
})
export class GlucoseModule { }
