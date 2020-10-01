import { Component, OnInit } from '@angular/core';
import { DoctorService } from 'src/app/doctor/doctor.service';
import { News } from 'src/app/doctor/doctor/news';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {

  news: News[];

  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
    
    this.doctorService.getNewsApi().subscribe(results => {
      // console.log(results.articles[0]);
      this.news = results.articles;
    })
  }

}
