import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  
  constructor(private http: HttpClient) { }
  readonly baseUrl = 'http://localhost:9000/v1/postNote';

  username = 'gpapado';
  password = 'gpapado';

  addNoteEntry(values):Observable<any>{
    return this.http.post(
      this.baseUrl+'/',
    {
        'gram':values.get('message').value,
        'date':values.get('date').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  updateNoteEntry(values):Observable<any>{
    return this.http.put(
      this.baseUrl+'/'+values.get('id').value,
    {
        'gram':values.get('message').value,
        'date':values.get('date').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }
}
