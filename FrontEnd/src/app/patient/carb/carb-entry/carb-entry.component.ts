import { DatePipe, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CarbService } from '../carb.service';

@Component({
  selector: 'app-carb-entry',
  templateUrl: './carb-entry.component.html',
  styleUrls: ['./carb-entry.component.scss'],
})
export class CarbEntryComponent implements OnInit {
  formCarbEntry: FormGroup;
  resultDiv: number = 0;

  constructor(private carbService: CarbService) {}

  ngOnInit(): void {
    this.formCarbEntry = new FormGroup({
      gram: new FormControl(null, Validators.required),
      date: new FormControl(null, Validators.required),
    });
  }

  clickCarbEntrySubmit() {
    this.carbService.addCarbEntry(this.formCarbEntry).subscribe((carbData) => {
      if (carbData.status == 200) this.resultDiv = 1;
      else this.resultDiv = -1;
      this.ngOnInit;
    });
  }
}
