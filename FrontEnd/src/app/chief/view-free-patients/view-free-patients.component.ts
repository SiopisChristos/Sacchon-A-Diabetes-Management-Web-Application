import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Patient } from 'src/app/patient/patient/patient';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-free-patients',
  templateUrl: './view-free-patients.component.html',
  styleUrls: ['./view-free-patients.component.scss']
})
export class ViewFreePatientsComponent implements OnInit {


  constructor(private chiefService: ChiefService ) {
   }
  listOfPatients: Patient[];
  days: number[];
  formViewFreePatients: FormGroup;

  ngOnInit(): void {
    this.formViewFreePatients = new FormGroup({
    })
  }
 
  clickViewFreePatients(){
    this.chiefService.getFreePatients(this.formViewFreePatients).subscribe(viewFreePatients => {
      this.listOfPatients = viewFreePatients.data[0];
      this.days = viewFreePatients.data[1];
      this.ngOnInit;
  })
}

}