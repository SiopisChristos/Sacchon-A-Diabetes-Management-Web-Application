import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPatientDataComponent } from './view-patient-data.component';

describe('ViewPatientDataComponent', () => {
  let component: ViewPatientDataComponent;
  let fixture: ComponentFixture<ViewPatientDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewPatientDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewPatientDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
