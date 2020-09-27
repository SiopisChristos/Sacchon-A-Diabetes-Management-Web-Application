import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-patient-data',
  templateUrl: './view-patient-data.component.html',
  styleUrls: ['./view-patient-data.component.scss']
})
export class ViewPatientDataComponent implements OnInit {

  constructor(private chiefService: ChiefService) { }
  formViewPatientData: FormGroup;
  ngOnInit(): void {


    this.formViewPatientData = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewPatientData(){
    this.chiefService.getPatientData(this.formViewPatientData).subscribe(viewPatientData => {
      alert(JSON.stringify(viewPatientData));
      this.ngOnInit;
  })
}

}