import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeePatientsDataComponent } from './see-patients-data.component';

describe('SeePatientsDataComponent', () => {
  let component: SeePatientsDataComponent;
  let fixture: ComponentFixture<SeePatientsDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SeePatientsDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeePatientsDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
