import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  formLogin: FormGroup
  username: string
  password: string

  constructor(private router: Router, private loginService: LoginService) { }

  ngOnInit(): void {
    this.formLogin = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    })
  }

  logIn() {
    //here you will subscribe to authorization when created as http get in the login.service
    //IMPORTANT*********
    
    // this.loginService.authorization(this.formLogin).subscribe(data => {
    //   if (data) { //here you check the text you return from the api
    //     this.username = this.formLogin.get('username').value;
    //     this.password = this.formLogin.get('password').value;
    //     sessionStorage.setItem("credentials", this.username + ":" + this.password)
    //     location.reload();
    //     this.router.navigate(['insertCarbEntry'])
    //   }
    //   else {
    //     alert("Wrong login or password");
    //   }
    // });
    
    var responseString = this.loginService.authorization(this.formLogin);
    if (responseString == "patient") {
      this.username = this.formLogin.get('username').value;
      this.password = this.formLogin.get('password').value;
      sessionStorage.setItem("credentials", this.username + ":" + this.password)
      location.reload();
      this.router.navigate(['patient'])
    }
    else if (responseString == "doctor") {
      this.username = this.formLogin.get('username').value;
      this.password = this.formLogin.get('password').value;
      sessionStorage.setItem("credentials", this.username + ":" + this.password)
      location.reload();
      this.router.navigate(['patient'])//when doctor.component is ready, add it here
    }
    else if (responseString == "admin") {
      this.username = this.formLogin.get('username').value;
      this.password = this.formLogin.get('password').value;
      sessionStorage.setItem("credentials", this.username + ":" + this.password)
      location.reload();
      this.router.navigate(['patient']) //when amdin.component is ready, add it here
    }
    else {
      alert("Wrong login or password");
    }
  }

}
