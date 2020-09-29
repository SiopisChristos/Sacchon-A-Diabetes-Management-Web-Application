import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/patient/patient/patient';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-chief',
  templateUrl: './chief.component.html',
  styleUrls: ['./chief.component.scss']
})
export class ChiefComponent implements OnInit {
  patients: Patient[];

  constructor(private chiefService: ChiefService) {
    
   }

  ngOnInit(): void {
    this.chiefService.getAllActivePatients().subscribe(result => {
      this.patients = result.data;
      this.ngOnInit;
  })
  }

}
