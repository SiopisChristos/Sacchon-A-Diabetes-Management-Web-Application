import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CarbEntryComponent } from './patient/carb/carb-entry/carb-entry.component';
import { CarbListComponent } from './patient/carb/carb-list/carb-list.component';
import { GlucoseEntryComponent } from './patient/glucose/glucose-entry/glucose-entry.component';

const routes: Routes = [
  {path: 'insertGlucoseEntry',  component: GlucoseEntryComponent},
  {path: 'insertCarbEntry',component: CarbEntryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
