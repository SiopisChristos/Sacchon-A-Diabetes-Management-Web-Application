import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-doctor-delete',
  templateUrl: './doctor-delete.component.html',
  styleUrls: ['./doctor-delete.component.scss'],
})
export class DoctorDeleteComponent implements OnInit {
  formDoctorDelete: FormGroup;
  constructor(private doctorDeleteService: DoctorService) {}

  ngOnInit(): void {
    this.formDoctorDelete = new FormGroup({});
  }
  clickDeleteDoctor() {
    this.doctorDeleteService.deleteDoctor().subscribe((doctorData) => {
      let message;
      if (doctorData.status == 200) 
        message = 'You deleted your account';
      else 
        message = "There was a problem. Your account was not deleted!"
      alert(message);
      this.ngOnInit;
    });
  }
}
