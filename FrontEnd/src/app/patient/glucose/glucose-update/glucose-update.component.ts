import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { GlucoseService } from '../glucose.service';

@Component({
  selector: 'app-glucose-update',
  templateUrl: './glucose-update.component.html',
  styleUrls: ['./glucose-update.component.scss']
})
export class GlucoseUpdateComponent implements OnInit {
  id:number = null;
  formGlucoseUpdate: FormGroup;

  constructor(private glucoseService: GlucoseService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    
    this.route.params.subscribe(params => {
      this.id = params.id;   
  });
    this.formGlucoseUpdate = new FormGroup({
      id: new FormControl(this.id, Validators.required),
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
