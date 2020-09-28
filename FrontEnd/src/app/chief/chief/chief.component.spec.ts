import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefComponent } from './chief.component';

describe('ChiefComponent', () => {
  let component: ChiefComponent;
  let fixture: ComponentFixture<ChiefComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChiefComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
