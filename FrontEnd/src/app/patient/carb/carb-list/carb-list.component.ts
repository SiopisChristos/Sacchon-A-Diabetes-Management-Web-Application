import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Carb } from '../carb';
import { CarbService } from '../carb.service';

@Component({
  selector: 'app-carb-list',
  templateUrl: './carb-list.component.html',
  styleUrls: ['./carb-list.component.scss']
})
export class CarbListComponent implements OnInit {

  constructor(private carbService: CarbService) { }

  resultDiv:number = 0;
  formAverageCarbIntake: FormGroup;
  s:number;
  ngOnInit(): void {
    this.formAverageCarbIntake = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
  
  clickAverageCarbIntake(){
    this.carbService.getAverageCarbIntake(this.formAverageCarbIntake).subscribe(carbAverageData => {
      console.log(carbAverageData);
      
      this.s=carbAverageData.data;
      if(carbAverageData.status != 200)
        this.resultDiv = -1
      else
        this.resultDiv = 1;
      this.ngOnInit;
      
  })
}

}