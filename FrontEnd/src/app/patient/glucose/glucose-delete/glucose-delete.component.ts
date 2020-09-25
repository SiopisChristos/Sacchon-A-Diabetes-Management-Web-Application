import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { GlucoseService } from '../glucose.service';

@Component({
  selector: 'app-glucose-delete',
  templateUrl: './glucose-delete.component.html',
  styleUrls: ['./glucose-delete.component.scss']
})
export class GlucoseDeleteComponent implements OnInit {

  formGlucoseDelete: FormGroup;

  constructor(private glucoseServiceDelete: GlucoseService) { }

  ngOnInit(): void {
    this.formGlucoseDelete = new FormGroup({
      id: new FormControl(null, Validators.required),
    })
  }

  clickGlucoseDeleteSubmit(){
    this.glucoseServiceDelete.deleteGlucoseEntry(this.formGlucoseDelete).subscribe(glucoseData => {
      alert(JSON.stringify(glucoseData));
      this.ngOnInit;
    })
  }

}
