import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlucoseEntryComponent } from './glucose-entry.component';

describe('GlucoseEntryComponent', () => {
  let component: GlucoseEntryComponent;
  let fixture: ComponentFixture<GlucoseEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlucoseEntryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlucoseEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
