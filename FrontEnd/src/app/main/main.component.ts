import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  isLogged:boolean;

  constructor(private router:Router) { }

  ngOnInit(): void {
    console.log(sessionStorage.getItem("credentials")+" app component")
    if(sessionStorage.getItem("credentials") === null){
      this.isLogged = false
      console.log("inside iff app component")
      this.router.navigate(['login'])
    }
    else{
      this.isLogged = true
      console.log("inside else app component")
      this.router.navigate(['patient'])
    }
  }

  logOut(){
    sessionStorage.removeItem("credentials")
    this.isLogged = false
    this.router.navigate(['login']) 
  }
}