import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  usuario = '';
  senha = '';
  erro = '';

  constructor(private router: Router) {}

  login() {
    if (this.usuario === 'admin' && this.senha === '1234') {
      this.erro = '';
      localStorage.setItem('usuario', this.usuario);
      this.router.navigate(['/noticias']);
    } else {
      this.erro = 'Usuário ou senha inválidos';
    }
  }

  voltarInicio() {
    this.router.navigate(['/']);
  }
}
