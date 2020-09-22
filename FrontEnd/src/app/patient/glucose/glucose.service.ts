import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GlucoseService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient/';

  username = 'christos';
  password = 'christos';

  addCarbEntry(values):Observable<any>{
    console.log(values.get('measurement').value);
    return this.http.post(
      this.baseUrl+'glucose/',
    {
        'measurement':values.get('measurement').value,
        'dateTime':values.get('dateTime').value,
        'patient':values.get('patient').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }
}
