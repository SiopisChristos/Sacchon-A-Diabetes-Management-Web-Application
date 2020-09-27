import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefService } from '../chief.service';

@Component({
  selector: 'app-view-inactive-doctors',
  templateUrl: './view-inactive-doctors.component.html',
  styleUrls: ['./view-inactive-doctors.component.scss']
})
export class ViewInactiveDoctorsComponent implements OnInit {

  constructor(private chiefService: ChiefService) { }

  formViewInactiveDoctors: FormGroup;
  ngOnInit(): void {
    this.formViewInactiveDoctors = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required)
    })
  }
 
  clickViewFreePatients(){
    this.chiefService.getInactiveDoctor(this.formViewInactiveDoctors).subscribe(viewInactiveDoctors => {
      alert(JSON.stringify(viewInactiveDoctors));
      this.ngOnInit;
  })
}

}