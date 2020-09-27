import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteEntryComponent } from './note/note-entry/note-entry.component';
import { NoteUpdateComponent } from './note/note-update/note-update.component';
import { DoctorComponent } from './doctor/doctor.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NoteModule } from './note/note.module';
import { Route } from '@angular/compiler/src/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {path: 'insertNote',component: NoteEntryComponent},
  {path: 'updateNote',component: NoteUpdateComponent}
  // {path: 'updateCarbEntry',component: },
  // {path: 'deleteCarbEntry',  component: CarbDeleteComponent},
];



@NgModule({
  declarations: [DoctorComponent],
  imports: [CommonModule, ReactiveFormsModule, NoteModule,RouterModule.forRoot(routes)],
  exports: [DoctorComponent]
})
export class DoctorModule {}
