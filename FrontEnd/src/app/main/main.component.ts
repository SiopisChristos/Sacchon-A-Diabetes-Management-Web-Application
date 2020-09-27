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

  constructor(private router:Router) { }

  ngOnInit(): void {
    this.role = sessionStorage.getItem("role")
    console.log(sessionStorage.getItem("credentials")+" app component")
    if(this.role === null){
      this.isLogged = false
      console.log("inside iff app component")
      this.router.navigate(['login'])
    }
    else{
      this.isLogged = true
      console.log("inside else app component")
      
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