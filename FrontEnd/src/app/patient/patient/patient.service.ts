import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient';

  username = sessionStorage.getItem("username");
  password = sessionStorage.getItem("password");

  registerPatient(values): Observable<any> {

    return this.http.post(
      this.baseUrl, {
      'username': values.get('username').value,
      'lastName': values.get('lastName').value,
      'firstName': values.get('firstName').value,
      'dateOfBirth': values.get('dateOfBirth').value,
      'address': values.get('address').value,
      'phoneNumber': values.get('phoneNumber').value,
      'city': values.get('city').value,
      'zipCode': values.get('zipCode').value

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
      'address': values.get('address').value,
      'phoneNumber': values.get('phoneNumber').value,
      'city': values.get('city').value,
      'zipCode': values.get('zipCode').value,
      'role': "patient"
    }, {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
    }
    )
  }
  patientDelete(): Observable<any> {
    return this.http.delete(this.baseUrl,
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })

      });
  }

  patientUpdate(values): Observable<any> {
    return this.http.put(
      this.baseUrl + '/update/' + values.get('id').value,
      {
        'lastName': values.get('lastName').value,
        'firstName': values.get('firstName').value,
        'dateOfBirth': values.get('dateOfBirth').value,
        'address': values.get('address').value,
        'phoneNumber': values.get('phoneNumber').value,
        'city': values.get('city').value,

      },
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );

  }

}
