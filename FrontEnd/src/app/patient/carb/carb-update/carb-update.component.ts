import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CarbService } from '../carb.service';

@Component({
  selector: 'app-carb-update',
  templateUrl: './carb-update.component.html',
  styleUrls: ['./carb-update.component.scss']
})
export class CarbUpdateComponent implements OnInit {
  id:number=null;
  formCarbUpdate: FormGroup;

  constructor(private carbService: CarbService, private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.id = params.id;   
  });
    
    this.formCarbUpdate = new FormGroup({
      id: new FormControl(this.id, Validators.required),
      gram: new FormControl(null, Validators.required),
      date: new FormControl(null, Validators.required)
    })
  }
  
  clickCarbUpdateSubmit(){
    this.carbService.updateCarbEntry(this.formCarbUpdate).subscribe(carbUpdateData => {
      alert(JSON.stringify(carbUpdateData));
      this.ngOnInit;
    })
  }

}
