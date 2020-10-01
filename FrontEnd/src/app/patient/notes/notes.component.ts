import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Note } from 'src/app/doctor/note/note';
import { NotificationObserverService } from 'src/app/notificationService/notification-observer.service';
import { PatientService } from '../patient/patient.service';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.scss'],
})
export class NotesComponent implements OnInit {
  constructor(
    private patientService: PatientService,
    private notificationObserver: NotificationObserverService
  ) {}

  listOfNotes: Note[];

  ngOnInit(): void {
    this.patientService.getMyNotes().subscribe((viewMyNotes) => {
      this.listOfNotes = viewMyNotes;
      console.log(viewMyNotes);
    });

    this.patientService.seenNotification().subscribe((result) => {
      console.log(result);
      
      if (result.data === true){ this.notificationObserver.propagateNotification(0);
      
      };
    });
  }

  clickViewMyNotes() {
    this.patientService.getMyNotes().subscribe((viewMyNotes) => {
      this.listOfNotes = viewMyNotes;

      this.ngOnInit;
    });
  }
}
