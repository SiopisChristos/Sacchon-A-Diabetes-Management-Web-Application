import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarbListComponent } from './carb-list/carb-list.component';
import { CarbEntryComponent } from './carb-entry/carb-entry.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CarbUpdateComponent } from './carb-update/carb-update.component';
import { CarbDeleteComponent } from './carb-delete/carb-delete.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: 'insertCarbEntry',component: CarbEntryComponent},
  {path: 'viewAverageCarbIntake',component: CarbListComponent},
  {path: 'updateCarbEntry',component: CarbUpdateComponent},
  {path: 'deleteCarbEntry',  component: CarbDeleteComponent},
];

@NgModule({
  declarations: [
    CarbListComponent, 
    CarbEntryComponent, 
    CarbUpdateComponent, 
    CarbDeleteComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule, 
    ReactiveFormsModule
  ],
  exports: [
    RouterModule,
    CarbListComponent, 
    CarbEntryComponent, 
    CarbUpdateComponent, 
    CarbDeleteComponent
  ]
})
export class CarbModule { }
