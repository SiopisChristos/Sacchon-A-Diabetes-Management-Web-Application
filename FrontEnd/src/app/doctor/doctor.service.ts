import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../patient/patient/patient';
import { PatientComponent } from '../patient/patient/patient.component';
import { News } from './doctor/news';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/doctor';

  username = sessionStorage.getItem("username");
  password = sessionStorage.getItem("password");


  UserRegistration(values): Observable<any> {
    return this.http.post('http://localhost:9000/v1/login',
     {
      username: values.get('username').value,
      password: values.get('password').value,
      lastName: values.get('lastName').value,
      firstName: values.get('firstName').value,
      dateOfBirth: values.get('dateOfBirth').value,
      diabetesType: values.get('diabetesType').value,
      phoneNumber: values.get('phoneNumber').value,
      specialty: values.get('specialty').value,
      role: "doctor"
    }, {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
    }
    );
  }

  getNewsApi(): Observable<any>{
    return this.http.get<any>
    ('http://newsapi.org/v2/top-headlines?country=us&apiKey=9c05e9cd9cf34a3d9a6b36ae0d63ae52', 
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      })
  }

  deleteDoctor(): Observable<any> {
    return this.http.delete(
      this.baseUrl + '/account'
      , {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );
  }

  getFreePatients(): Observable<any> {
    return this.http.get(
      this.baseUrl + '/see/freePatients',
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );
  }

  getPatientsData(patient_id):Observable<any>{
    return this.http.get('http://localhost:9000/v1/record/' + patient_id,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  getMyPatients(): Observable<any> {
    return this.http.get(
      this.baseUrl + '/see/myPatients',
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password) })
      }
    );
  }

  choosePatient(id): Observable<any> {
    return this.http.put(
      this.baseUrl + '/account/' + id, {},
      {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})
      }
    );
  }
  
}