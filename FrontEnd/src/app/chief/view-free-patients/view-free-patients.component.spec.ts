import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFreePatientsComponent } from './view-free-patients.component';

describe('ViewFreePatientsComponent', () => {
  let component: ViewFreePatientsComponent;
  let fixture: ComponentFixture<ViewFreePatientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewFreePatientsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewFreePatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
