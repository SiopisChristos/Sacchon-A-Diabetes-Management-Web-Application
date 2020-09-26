import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-inactive-patients',
  templateUrl: './view-inactive-patients.component.html',
  styleUrls: ['./view-inactive-patients.component.scss']
})
export class ViewInactivePatientsComponent implements OnInit {

  constructor(private chiefService: ChiefService) { }

  formViewInactivePatients: FormGroup;

  ngOnInit(): void {

    this.formViewInactivePatients = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewFreePatients(){
    this.chiefService.getInactivePatients(this.formViewInactivePatients).subscribe(viewInactivePatients => {
      alert(JSON.stringify(viewInactivePatients));
      this.ngOnInit;
  })
}

}