import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GlucoseService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient/glucose';

  username = sessionStorage.getItem("username");
  password = sessionStorage.getItem("password");

  getAverageGlucoseIntake(values):Observable<any>{
    return this.http.get(
      this.baseUrl+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  addGlucoseEntry(values):Observable<any>{
    return this.http.post(
      this.baseUrl+'/',
    {
        'measurement':values.get('measurement').value,
        'dateTime':values.get('dateTime').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  updateGlucoseEntry(values):Observable<any>{
    return this.http.put(
      this.baseUrl+'/'+values.get('id').value,
    {
        'measurement':values.get('measurement').value,
        'dateTime':values.get('dateTime').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  deleteGlucoseEntry(id): Observable<any> {
    return this.http.delete(
      this.baseUrl+'/'+id.get('id').value,
  {
    headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
  );
  }

}
