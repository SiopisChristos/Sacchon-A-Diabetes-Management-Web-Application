import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  params = new HttpParams();

  readonly baseUrl = 'http://localhost:9000/v1';

  authorization(values): string{ //Observable<any> {
    this.params.append('username', values.get('username').value);
    this.params.append('password', values.get('password').value);
    if (values.get('username').value == "ekelid" && values.get('password').value == "ekelid") {
      return "patient"
      //return this.http.get<any>(this.baseUrl+'/auth',{ params:this.params});
    }
    else if (values.get('username').value == "gpapado" && values.get('password').value == "gpapado") {
      return "doctor"
      // return this.http.get(this.baseUrl+'/login',{params:this.params});
    }
    else if (values.get('username').value == "admin" && values.get('password').value == "admin") {
      return "admin"
      // return this.http.get(this.baseUrl+'/login',{params:this.params});
    }
    else {
      return "NOT OK";
    }
  }
    //Here you make the call to the API to authenticate the user
    //change the signature of method to Observable<any>
    //return this.http.get<any>(this.baseUrl+'/auth',{ params:this.params});
}
