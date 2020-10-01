import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { GlucoseService } from '../glucose.service';

@Component({
  selector: 'app-glucose-entry',
  templateUrl: './glucose-entry.component.html',
  styleUrls: ['./glucose-entry.component.scss']
})
export class GlucoseEntryComponent implements OnInit {

  formGlucoseEntry: FormGroup;
  resultDiv:number;

  constructor(private glucoseService: GlucoseService) { }

  ngOnInit(): void {
    this.formGlucoseEntry = new FormGroup({
      measurement: new FormControl(null, Validators.required),
      dateTime: new FormControl(null, Validators.required)
    })
  }

  
  clickGlucoseEntrySubmit(){
    this.glucoseService.addGlucoseEntry(this.formGlucoseEntry).subscribe(glucoseData => {
      
      if(glucoseData.data)
        this.resultDiv = 1
      else
        this.resultDiv = -1;
      this.ngOnInit;
    })
  }

}
