import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlucoseUpdateComponent } from './glucose-update.component';

describe('GlucoseUpdateComponent', () => {
  let component: GlucoseUpdateComponent;
  let fixture: ComponentFixture<GlucoseUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlucoseUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlucoseUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
