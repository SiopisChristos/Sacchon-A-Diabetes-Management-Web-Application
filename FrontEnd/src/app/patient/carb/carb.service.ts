import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Carb } from './carb';


@Injectable({
  providedIn: 'root'
})
export class CarbService {

  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/patient/carb';

  username = 'ekelid';
  password = 'ekelid';

  getAverageCarbIntake(values):Observable<any>{
    return this.http.get(
      this.baseUrl+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  addCarbEntry(values):Observable<any>{
    return this.http.post(
      this.baseUrl+'/',
    {
        'gram':values.get('gram').value,
        'date':values.get('date').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  updateCarbEntry(values):Observable<any>{
    return this.http.put(
      this.baseUrl+'/'+values.get('id').value,
    {
        'gram':values.get('gram').value,
        'date':values.get('date').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  deleteCarbEntry(id): Observable<any> {
    return this.http.delete(
      this.baseUrl+'/'+id.get('id').value,
  {
    headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
  );
  }

}
