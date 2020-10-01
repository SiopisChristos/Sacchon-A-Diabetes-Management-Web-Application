import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationObserverService } from '../notificationService/notification-observer.service';
import { PatientService } from '../patient/patient/patient.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  
  isLogged:boolean;
  role:String;
  username: String;
  notification:number ;

  constructor(private router:Router,private notificationObserver: NotificationObserverService, private patientService: PatientService) { }

  ngOnInit(): void {
    this.role = sessionStorage.getItem("role")
    if(this.role === null){
      this.isLogged = false
      console.log("inside iff app component")
      this.router.navigate(['login'])
    }
    else{
      this.isLogged = true
      this.username = sessionStorage.getItem("username")
      console.log("inside else app component")      
    }
    if (this.role === 'patient') {
      this.notificationObserver.numberOfNotifications.subscribe(data => this.notification = data );
      this.patientService.getMyNotes().subscribe(data=>{ 
        let number = 0;
        data.forEach(element => {
          if(!element.seen)
            number++;
        });
        this.notification = number; 
      })
    }

    
  }

  logOut(){
    sessionStorage.removeItem("username")
    sessionStorage.removeItem("password")
    sessionStorage.removeItem("role")
    this.isLogged = false
    this.router.navigate(['login']) 
  }

  
}