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

  private getTime(date?: Date) {
    return date != null ? new Date(date).getTime() : 0;
}
  constructor(
    private patientService: PatientService,
    private notificationObserver: NotificationObserverService
  ) {}

  listOfNotes: Note[];

  ngOnInit(): void {
    this.patientService.getMyNotes().subscribe((viewMyNotes) => {
      this.listOfNotes = viewMyNotes;
      this.listOfNotes.sort((a: Note, b: Note)=>{
        return this.getTime(b.date) - this.getTime(a.date);
      });
      console.log(viewMyNotes);
      for (let x of this.listOfNotes) {
        if (!x.seen) {
          this.patientService.seenNotification().subscribe((result) => {
            if (result.status == 200) {
              this.notificationObserver.propagateNotification(0);
            }
          });
          break;
        }
      }
    });

  }
}
