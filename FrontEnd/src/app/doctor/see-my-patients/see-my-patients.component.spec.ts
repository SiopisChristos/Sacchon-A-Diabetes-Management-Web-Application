import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeeMyPatientsComponent } from './see-my-patients.component';

describe('SeeMyPatientsComponent', () => {
  let component: SeeMyPatientsComponent;
  let fixture: ComponentFixture<SeeMyPatientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SeeMyPatientsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeeMyPatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
