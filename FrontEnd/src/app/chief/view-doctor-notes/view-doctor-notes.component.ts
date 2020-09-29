import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Doctor } from 'src/app/doctor/doctor';
import { Note } from 'src/app/doctor/note/note';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-doctor-notes',
  templateUrl: './view-doctor-notes.component.html',
  styleUrls: ['./view-doctor-notes.component.scss']
})
export class ViewDoctorNotesComponent implements OnInit {

  id:number;
  listOfNotes: Note[];
  constructor(private chiefService: ChiefService, private route: ActivatedRoute) { }
  formViewDoctorNotes: FormGroup;

  ngOnInit(): void {

    
    this.route.params.subscribe(params => {
      this.id = params.id;   
  });

    this.formViewDoctorNotes = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  
  clickViewDoctorNotes(){
    this.chiefService.getDoctorNotes(this.formViewDoctorNotes,this.id).subscribe(result => {
      this.listOfNotes = result.data;
      this.ngOnInit;
  })
}

}