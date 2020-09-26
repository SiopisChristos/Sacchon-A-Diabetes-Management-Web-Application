import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDoctorNotesComponent } from './view-doctor-notes.component';

describe('ViewDoctorNotesComponent', () => {
  let component: ViewDoctorNotesComponent;
  let fixture: ComponentFixture<ViewDoctorNotesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDoctorNotesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewDoctorNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
