import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Carb } from 'src/app/patient/carb/carb';
import { Glucose } from 'src/app/patient/glucose/glucose';
import { Patient } from 'src/app/patient/patient/patient';
import { DoctorService } from '../doctor.service';
import { Note } from '../note/note';
import { NoteService } from '../note/note.service';

@Component({
  selector: 'app-see-my-patients',
  templateUrl: './see-my-patients.component.html',
  styleUrls: ['./see-my-patients.component.scss']
})
export class SeeMyPatientsComponent implements OnInit {

  constructor(
    private doctorService: DoctorService, 
    private route: ActivatedRoute,
    private noteService: NoteService
    ) { }

  listOfMyPatients: Patient[];
  data1: Carb[];
  data2: Glucose[];
  data3: Note[];

  ngOnInit(): void {
    this.doctorService.getMyPatients().subscribe(
      listMypatient => { this.listOfMyPatients = listMypatient.data; 
    })
  }

  seedata(patient_id) {
    this.doctorService.getPatientsData(patient_id).subscribe(lis => {
      this.data1 = lis.data[0];
      this.data2 = lis.data[1];
      this.data3 = lis.data[2];
      this.ngOnInit;
    });
  }

}
