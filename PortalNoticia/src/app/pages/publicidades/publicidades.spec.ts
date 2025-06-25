import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Publicidades } from './publicidades';

describe('Publicidades', () => {
  let component: Publicidades;
  let fixture: ComponentFixture<Publicidades>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Publicidades]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Publicidades);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
