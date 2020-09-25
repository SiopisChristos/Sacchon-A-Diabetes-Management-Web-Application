import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarbUpdateComponent } from './carb-update.component';

describe('CarbUpdateComponent', () => {
  let component: CarbUpdateComponent;
  let fixture: ComponentFixture<CarbUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarbUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarbUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
