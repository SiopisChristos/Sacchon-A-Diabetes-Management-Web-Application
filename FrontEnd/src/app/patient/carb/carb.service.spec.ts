import { TestBed } from '@angular/core/testing';

import { CarbService } from './carb.service';

describe('CarbService', () => {
  let service: CarbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
