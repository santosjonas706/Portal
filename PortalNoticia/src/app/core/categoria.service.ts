import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Categoria {
  id?: number;
  descricao: string;
  categoriaPaiId?: number;
  subcategoriasIds?: number[];
}

@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private api = 'http://localhost:8080/categoria';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.api);
  }

  getById(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.api}/${id}`);
  }

  create(categoria: Categoria): Observable<any> {
    return this.http.post(this.api, categoria);
  }

  update(id: number, categoria: Categoria): Observable<any> {
    return this.http.put(`${this.api}/${id}`, categoria);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.api}/${id}`);
  }
}
