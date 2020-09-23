import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarbListComponent } from './carb-list.component';

describe('CarbListComponent', () => {
  let component: CarbListComponent;
  let fixture: ComponentFixture<CarbListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarbListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarbListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
