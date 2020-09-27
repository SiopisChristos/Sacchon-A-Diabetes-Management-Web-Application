import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-free-patients',
  templateUrl: './view-free-patients.component.html',
  styleUrls: ['./view-free-patients.component.scss']
})
export class ViewFreePatientsComponent implements OnInit {


  constructor(private chiefService: ChiefService ) { }

  formViewFreePatients: FormGroup;

  ngOnInit(): void {
    this.formViewFreePatients = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewFreePatients(){
    this.chiefService.getFreePatients(this.formViewFreePatients).subscribe(viewFreePatients => {
      alert(JSON.stringify(viewFreePatients));
      this.ngOnInit;
  })
}

}