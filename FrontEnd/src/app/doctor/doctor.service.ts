import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/doctor';

  username = sessionStorage.getItem("username");
  password = sessionStorage.getItem("password");

  registerDoctor(values): Observable<any> {
    return this.http.post(
      this.baseUrl + '/', {
      'firstName': values.get('firstName').value,
      'lastName': values.get('lastName').value,
      'userName': values.get('username').value,
      'phoneNumber': values.get('phoneNumber').value,
      'dateOfBirth': values.get('dateOfBirth').value,
      'creationDate': values.get('creationDate').value,
      'specialty': values.get('specialty').value,
    },
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );
  }
  UserRegistration(values): Observable<any> {
    return this.http.post('http://localhost:9000/v1/login', {
      'username': values.get('username').value,
      'password': values.get('password').value,
      'lastName': values.get('lastName').value,
      'firstName': values.get('firstName').value,
      'dateOfBirth': values.get('dateOfBirth').value,
      'phoneNumber': values.get('phoneNumber').value,
      'specialty': values.get('specialty').value,
      'role': "doctor"
    }, {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
    }
    );
  }
  deleteDoctor(): Observable<any> {
    return this.http.delete(
      this.baseUrl + '/account'
      , {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );
  }
}