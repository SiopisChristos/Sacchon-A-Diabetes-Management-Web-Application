import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewInactiveDoctorsComponent } from './view-inactive-doctors.component';

describe('ViewInactiveDoctorsComponent', () => {
  let component: ViewInactiveDoctorsComponent;
  let fixture: ComponentFixture<ViewInactiveDoctorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewInactiveDoctorsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewInactiveDoctorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
