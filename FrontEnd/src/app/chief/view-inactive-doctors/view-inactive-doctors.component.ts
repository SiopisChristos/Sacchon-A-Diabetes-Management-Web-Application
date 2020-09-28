import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Doctor } from 'src/app/doctor/doctor';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-inactive-doctors',
  templateUrl: './view-inactive-doctors.component.html',
  styleUrls: ['./view-inactive-doctors.component.scss']
})
export class ViewInactiveDoctorsComponent implements OnInit {

  constructor(private chiefService: ChiefService) { }
  listOfDoctors: Doctor[];
  formViewInactiveDoctors: FormGroup;

  ngOnInit(): void {
    this.formViewInactiveDoctors = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewInactiveDoctors(){
    this.chiefService.getInactiveDoctor(this.formViewInactiveDoctors).subscribe(viewInactiveDoctors => {
      this.listOfDoctors = viewInactiveDoctors.data;
      this.ngOnInit;
  })
}

}