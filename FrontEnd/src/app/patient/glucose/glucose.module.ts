import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { GlucoseListComponent } from './glucose-list/glucose-list.component';
import { GlucoseEntryComponent } from './glucose-entry/glucose-entry.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GlucoseUpdateComponent } from './glucose-update/glucose-update.component';
import { GlucoseDeleteComponent } from './glucose-delete/glucose-delete.component';
import { RouterModule, Routes } from '@angular/router';
import { ChartsModule } from 'ng2-charts';


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
    ChartsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule,
    GlucoseEntryComponent,
    GlucoseListComponent,
    GlucoseDeleteComponent,
    GlucoseUpdateComponent
  ],
  providers: [DatePipe]
})
export class GlucoseModule { }
