import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  
  constructor(private http: HttpClient) { }
  readonly postUrl = 'http://localhost:9000/v1/postNote';
  readonly updateUrl = 'http://localhost:9000/v1/record';

  username = 'gpapado';
  password = 'gpapado';

  addNoteEntry(values):Observable<any>{
    return this.http.post(
      this.postUrl,
    {
        'note':values.get('message').value,
        'date':values.get('date').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }

  updateNoteEntry(values):Observable<any>{
    
    return this.http.put(
      this.updateUrl+'/'+values.get('id').value,
    {
        'message':values.get('message').value
    },
    {
      headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)})}
    );
  }
}