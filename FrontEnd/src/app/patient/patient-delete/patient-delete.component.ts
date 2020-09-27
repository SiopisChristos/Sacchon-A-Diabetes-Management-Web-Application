import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-patient-delete',
  templateUrl: './patient-delete.component.html',
  styleUrls: ['./patient-delete.component.scss']
})
export class PatientDeleteComponent implements OnInit {

  formPatientDelete: FormGroup

  constructor(private patientDeleteService: PatientService) { }

  ngOnInit(): void {
    this.formPatientDelete = new FormGroup({

    })
  }

  clickDeletePatient() {
    this.patientDeleteService.patientDelete().subscribe(patientData => {
      alert(JSON.stringify(patientData));
      this.ngOnInit;
    })
  }

}
