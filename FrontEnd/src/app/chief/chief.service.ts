import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChiefService {

  constructor(private http: HttpClient) { }
  readonly getInactiveDoctorUrl =  "http://localhost:9000/v1/doctorsInactive";
  readonly getInactivePatientsUrl = "http://localhost:9000/v1/patientsInactive";
  readonly getDoctorNotesUrl = "http://localhost:9000/v1/doctorNotes";
  readonly getPatientDataUrl = "http://localhost:9000/v1//patientData";
  readonly getFreePatientsUrl = "http://localhost:9000/v1/patientsInactiveAndTime";

  username = sessionStorage.getItem("username");
  password = sessionStorage.getItem("password");

  getInactiveDoctor(values):Observable<any>{
    return this.http.get(
      this.getInactiveDoctorUrl+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  getInactivePatients(values):Observable<any>{
    return this.http.get(
      this.getInactivePatientsUrl+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  getDoctorNotes(values):Observable<any>{
    return this.http.get(
      this.getDoctorNotesUrl+'/'+values.get('id').value+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  getPatientData(values):Observable<any>{
    return this.http.get(
      this.getPatientDataUrl+'/'+values.get('id').value+'?from='+values.get('from').value+'&to='+values.get('to').value,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

  getFreePatients(values):Observable<any>{
    return this.http.get(
      this.getFreePatientsUrl,
        {
          headers:new HttpHeaders({'Authorization': 'Basic ' + btoa( this.username+':'+this.password)})}
        );
  }

}

