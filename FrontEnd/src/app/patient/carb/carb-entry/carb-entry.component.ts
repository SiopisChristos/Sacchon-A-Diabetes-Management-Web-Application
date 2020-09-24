import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CarbService } from '../carb.service';

@Component({
  selector: 'app-carb-entry',
  templateUrl: './carb-entry.component.html',
  styleUrls: ['./carb-entry.component.scss']
})
export class CarbEntryComponent implements OnInit {

  formCarbEntry: FormGroup;
  
  constructor(private carbService: CarbService) { }

  ngOnInit(): void {
    this.formCarbEntry = new FormGroup({
      gram: new FormControl(null, Validators.required),
      date: new FormControl
    })
  }

  clickCarbEntrySubmit(){
    this.carbService.addCarbEntry(this.formCarbEntry).subscribe(carbData => {
      alert(JSON.stringify(carbData));
      this.ngOnInit;
    })
  }

}
