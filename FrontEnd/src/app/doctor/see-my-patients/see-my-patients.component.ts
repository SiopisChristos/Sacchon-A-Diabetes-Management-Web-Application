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

  private getTime(date?: Date) {
    return date != null ? new Date(date).getTime() : 0;
}
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

      this.data1.sort((a: Carb, b: Carb)=>{
        return this.getTime(b.date) - this.getTime(a.date);
      });

      this.data2.sort((a: Glucose, b: Glucose)=>{
        return this.getTime(b.dateTime) - this.getTime(a.dateTime);
      });
      
      this.data3.sort((a: Note, b: Note)=>{
        return this.getTime(b.date) - this.getTime(a.date);
      });
      

      this.ngOnInit;
    });
  }

}
