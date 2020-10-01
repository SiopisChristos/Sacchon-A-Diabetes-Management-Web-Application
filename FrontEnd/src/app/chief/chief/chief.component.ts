import { Component, OnInit } from '@angular/core';
import { Doctor } from 'src/app/doctor/doctor';
import { Patient } from 'src/app/patient/patient/patient';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-chief',
  templateUrl: './chief.component.html',
  styleUrls: ['./chief.component.scss']
})
export class ChiefComponent implements OnInit {
  patients: Patient[];
 doctors: Doctor[];
viewpatient: boolean=true;

  constructor(private chiefService: ChiefService) {
    
   }

  ngOnInit(): void {
    this.chiefService.getAllActivePatients().subscribe(result => {
      this.patients = result.data;
      this.ngOnInit;
  })

    this.chiefService.getAllActiveDoctors().subscribe(result => {
      this.doctors = result.data;
      this.ngOnInit;
  })
  
  }

  viewEntity(value) {
  this.viewpatient=value;
  }


}
