import { TestBed } from '@angular/core/testing';

import { NotificationObserverService } from './notification-observer.service';

describe('NotificationObserverService', () => {
  let service: NotificationObserverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationObserverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
