import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewInactivePatientsComponent } from './view-inactive-patients.component';

describe('ViewInactivePatientsComponent', () => {
  let component: ViewInactivePatientsComponent;
  let fixture: ComponentFixture<ViewInactivePatientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewInactivePatientsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewInactivePatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
