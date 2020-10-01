import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationObserverService {

  numberOfNotifications = new Subject<number>();

  propagateNotification(value:number){
    this.numberOfNotifications.next(value)
  }
  
}