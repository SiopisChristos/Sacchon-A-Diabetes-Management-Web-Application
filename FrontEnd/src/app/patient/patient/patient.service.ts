import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  constructor(private http: HttpClient) {}
  readonly baseUrl = 'http://localhost:9000/v1/patient';
  readonly baseUrlGlucose = 'http://localhost:9000/v1/patient/glucose';
  readonly baseUrlCarb = 'http://localhost:9000/v1/patient/carb';

  username = sessionStorage.getItem('username');
  password = sessionStorage.getItem('password');

  UserRegistration(values): Observable<any> {
    return this.http.post(
      'http://localhost:9000/v1/login',
      {
        username: values.get('username').value,
        password: values.get('password').value,
        lastName: values.get('lastName').value,
        firstName: values.get('firstName').value,
        dateOfBirth: values.get('dateOfBirth').value,
        address: values.get('address').value,
        phoneNumber: values.get('phoneNumber').value,
        city: values.get('city').value,
        zipCode: values.get('zipCode').value,
        diabetesType: values.get('diabetesType').value,
        role: 'patient',
      },
      {
        headers: new HttpHeaders({
          Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
        }),
      }
    );
  }

  patientDelete(): Observable<any> {
    return this.http.delete(this.baseUrl, {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

  patientUpdate(values): Observable<any> {
    return this.http.put(
      this.baseUrl + '/update/' + values.get('id').value,
      {
        lastName: values.get('lastName').value,
        firstName: values.get('firstName').value,
        dateOfBirth: values.get('dateOfBirth').value,
        address: values.get('address').value,
        phoneNumber: values.get('phoneNumber').value,
        city: values.get('city').value,
      },
      {
        headers: new HttpHeaders({
          Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
        }),
      }
    );
  }

  patientGetMyData(): Observable<any> {
    return this.http.get(this.baseUrl, {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

  deleteGlucoseEntry(id): Observable<any> {
    return this.http.delete(this.baseUrlGlucose + '/' + id, {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

  deleteCarbEntry(id): Observable<any> {
    return this.http.delete(this.baseUrlCarb + '/' + id, {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

  getMyNotes(): Observable<any> {
    return this.http.get(this.baseUrl + '/note/', {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

  seenNotification(): Observable<any> {
    return this.http.post(this.baseUrl+'/records',{}, {
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
      }),
    });
  }

}
