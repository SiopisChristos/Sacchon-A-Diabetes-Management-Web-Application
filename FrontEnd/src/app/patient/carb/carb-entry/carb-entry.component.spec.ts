import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarbEntryComponent } from './carb-entry.component';

describe('CarbEntryComponent', () => {
  let component: CarbEntryComponent;
  let fixture: ComponentFixture<CarbEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarbEntryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarbEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
