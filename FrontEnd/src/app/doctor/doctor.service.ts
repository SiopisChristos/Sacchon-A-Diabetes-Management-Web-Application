import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from './doctor';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly endpoint = 'http://localhost:9000/v1/doctor/see/freePatients';

 constructor(private http: HttpClient) { }

 getDoctors(): Observable<any> {
  return this.http.get<Doctor>(this.endpoint);
 }

}
