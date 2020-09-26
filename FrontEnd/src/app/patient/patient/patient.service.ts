import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient';

  username='ekelid';
  password='ekelid';

  registerPatient(values):Observable<any>{
    return this.http.post(
      this.baseUrl,{
        'username':values.get('username').value,
        'lastName':values.get('lastName').value,
        'firstName':values.get('firstName').value,
        'dateOfBirth':values.get('dateOfBirth').value,

      },
      {
        headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
      );
    
  }

}
