import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientMyDataComponent } from './patient-my-data.component';

describe('PatientMyDataComponent', () => {
  let component: PatientMyDataComponent;
  let fixture: ComponentFixture<PatientMyDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientMyDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientMyDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
