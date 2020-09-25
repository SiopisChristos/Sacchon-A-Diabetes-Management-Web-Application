import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { GlucoseService } from '../glucose.service';

@Component({
  selector: 'app-glucose-update',
  templateUrl: './glucose-update.component.html',
  styleUrls: ['./glucose-update.component.scss']
})
export class GlucoseUpdateComponent implements OnInit {

  formGlucoseUpdate: FormGroup;

  constructor(private glucoseService: GlucoseService) { }

  ngOnInit(): void {
    this.formGlucoseUpdate = new FormGroup({
      id: new FormControl(null, Validators.required),
      measurement: new FormControl(null, Validators.required),
      dateTime: new FormControl(null, Validators.required)
    })
  }
  
  clickGlucoseUpdateSubmit(){
    this.glucoseService.updateGlucoseEntry(this.formGlucoseUpdate).subscribe(glucoseUpdateData => {
      alert(JSON.stringify(glucoseUpdateData));
      this.ngOnInit;
    })
  }

}
