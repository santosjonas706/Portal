import { TestBed } from '@angular/core/testing';

import { Noticia } from './noticia';

describe('Noticia', () => {
  let service: Noticia;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Noticia);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
