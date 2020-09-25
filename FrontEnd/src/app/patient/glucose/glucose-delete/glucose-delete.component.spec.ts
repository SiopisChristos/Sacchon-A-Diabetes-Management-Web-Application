import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlucoseDeleteComponent } from './glucose-delete.component';

describe('GlucoseDeleteComponent', () => {
  let component: GlucoseDeleteComponent;
  let fixture: ComponentFixture<GlucoseDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlucoseDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlucoseDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
