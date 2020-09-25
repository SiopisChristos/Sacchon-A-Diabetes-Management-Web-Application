import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CarbService } from '../carb.service';

@Component({
  selector: 'app-carb-delete',
  templateUrl: './carb-delete.component.html',
  styleUrls: ['./carb-delete.component.scss']
})
export class CarbDeleteComponent implements OnInit {

  formCarbDelete: FormGroup;

  constructor(private carbServiceDelete: CarbService) { }

  ngOnInit(): void {
    this.formCarbDelete = new FormGroup({
      id: new FormControl(null, Validators.required),
    })
  }

  clickCarbDeleteSubmit(){
    this.carbServiceDelete.deleteCarbEntry(this.formCarbDelete).subscribe(carbData => {
      alert(JSON.stringify(carbData));
      this.ngOnInit;
    })
  }

}
