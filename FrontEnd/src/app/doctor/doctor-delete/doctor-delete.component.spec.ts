import { Component, OnInit } from '@angular/core';
import { Form, FormGroup } from '@angular/forms';
import { DoctorService } from '../doctor.service';

@Component({
  selector: 'app-doctor-delete',
  templateUrl: './doctor-delete.component.html',
  styleUrls: ['./doctor-delete.component.scss']
})
export class DoctorDeleteComponent implements OnInit {

  formDoctorDelete:FormGroup
  constructor(private doctorDeleteService:DoctorService) { }

  ngOnInit(): void {
    this.formDoctorDelete=new FormGroup({
      
    })
  }
  clickDeleteDoctor(){
    this.doctorDeleteService.deleteDoctor().subscribe(doctorData=>{
      alert(JSON.stringify(doctorData));
      this.ngOnInit;  
  })
}
}