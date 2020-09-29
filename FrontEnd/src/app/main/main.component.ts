import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  isLogged:boolean;
  role:String;
  username: String;
  notification:boolean = true;
  

  constructor(private router:Router) { }

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
      //subscribe to event for notifications
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