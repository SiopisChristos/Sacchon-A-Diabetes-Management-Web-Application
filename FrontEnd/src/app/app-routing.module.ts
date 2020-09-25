import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CarbDeleteComponent } from './patient/carb/carb-delete/carb-delete.component';
import { CarbEntryComponent } from './patient/carb/carb-entry/carb-entry.component';
import { CarbListComponent } from './patient/carb/carb-list/carb-list.component';
import { CarbUpdateComponent } from './patient/carb/carb-update/carb-update.component';
import { GlucoseDeleteComponent } from './patient/glucose/glucose-delete/glucose-delete.component';
import { GlucoseEntryComponent } from './patient/glucose/glucose-entry/glucose-entry.component';
import { GlucoseListComponent } from './patient/glucose/glucose-list/glucose-list.component';
import { GlucoseUpdateComponent } from './patient/glucose/glucose-update/glucose-update.component';

const routes: Routes = [
  {path: 'insertCarbEntry',component: CarbEntryComponent},
  {path: 'viewAverageCarbIntake',component: CarbListComponent},
  {path: 'updateCarbEntry',component: CarbUpdateComponent},
  {path: 'deleteCarbEntry',  component: CarbDeleteComponent},
  {path: 'insertGlucoseEntry',  component: GlucoseEntryComponent},
  {path: 'viewAverageGlucoseIntake',component: GlucoseListComponent},
  {path: 'updateGlucoseEntry',component: GlucoseUpdateComponent},
  {path: 'deleteGlucoseEntry',  component: GlucoseDeleteComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
