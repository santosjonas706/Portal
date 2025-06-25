import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriaService, Categoria } from '../../core/categoria.service';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categorias.html',
  styleUrls: ['./categorias.css']
})
export class Categorias {
  categorias: Categoria[] = [];

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit() {
    this.categoriaService.getAll().subscribe(data => this.categorias = data);
  }
}
