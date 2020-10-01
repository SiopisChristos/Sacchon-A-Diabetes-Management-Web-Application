import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../doctor.service';
import { News } from './news';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  news: News[];

  constructor(private doctorService: DoctorService) { }

  ngOnInit(): void {
    
    this.doctorService.getNewsApi().subscribe(results => {
      // console.log(results.articles[0]);
      this.news = results.articles;
    })
  }

}
