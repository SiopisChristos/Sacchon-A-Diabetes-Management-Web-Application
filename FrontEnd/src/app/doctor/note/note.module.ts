import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteUpdateComponent } from './note-update/note-update.component';
import { NoteEntryComponent } from './note-entry/note-entry.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [NoteUpdateComponent, NoteEntryComponent],
  imports: [
    CommonModule, ReactiveFormsModule
  ],
  exports: [NoteEntryComponent, NoteUpdateComponent]
})
export class NoteModule { }
