import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarbDeleteComponent } from './carb-delete.component';

describe('CarbDeleteComponent', () => {
  let component: CarbDeleteComponent;
  let fixture: ComponentFixture<CarbDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarbDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarbDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
