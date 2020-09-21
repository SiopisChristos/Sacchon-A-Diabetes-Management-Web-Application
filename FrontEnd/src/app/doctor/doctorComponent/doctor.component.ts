import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../doctor.service';


@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  constructor(private doctorService: DoctorService) { }
  doctor: Array<string>;

  ngOnInit(): void {
    this.doctorService.getDoctors().subscribe((doctor) => {
      this.doctor = doctor.map((it) => {
        return it.fisrtName + it.lastName;
      });
    });
  }
}
