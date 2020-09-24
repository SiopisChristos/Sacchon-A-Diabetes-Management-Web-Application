import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carb } from './carb';

@Injectable({
  providedIn: 'root'
})
export class CarbService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient/';

  username = 'ekelid';
  password = 'ekelid';

  addCarbEntry(values):Observable<any>{
    console.log(values.get('gram').value);
    return this.http.post(
      this.baseUrl+'carb/',
    {
        'gram':values.get('gram').value,
        'date':values.get('date').value,
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }
}
