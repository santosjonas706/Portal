import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  template: `
    <nav>
      <div style="flex:1;"></div>
      <span class="nav-title">Portal de Notícias</span>
      <div class="nav-menu-container">
        <button class="nav-menu-btn" (click)="toggleMenu()">
          <span style="font-size:1.2rem;">☰</span>
        </button>
        <div class="nav-menu-dropdown" *ngIf="menuAberto">
          <a (click)="irParaHome(); fecharMenu()">Página Inicial</a>
          <a *ngIf="logado" (click)="irParaConfiguracoes(); fecharMenu()">Configurações</a>
          <a *ngIf="logado" (click)="irParaNoticias(); fecharMenu()">Gerenciar Notícias</a>
          <a *ngIf="logado" (click)="logout(); fecharMenu()">Sair</a>
          <a *ngIf="!logado" (click)="irParaLogin(); fecharMenu()">Login</a>
        </div>
      </div>
    </nav>
    <router-outlet></router-outlet>
  `,
  styles: [`
    nav {
      display: flex;
      align-items: center;
      padding: 1rem;
      background: #f5f5f5;
    }
    .nav-title {
      flex: 22;
      text-align: center;
      font-size: 25px;
      font-weight: 700;
    }
    .nav-menu-container {
      position: relative;
      margin-left: auto;
    }
    .nav-menu-btn {
      background: #fff;
      color: #e50914;
      border: none;
      border-radius: 6px;
      padding: 0.5rem 1.2rem;
      font-weight: 700;
      cursor: pointer;
      font-size: 1.1rem;
      transition: background 0.2s, color 0.2s;
    }
    .nav-menu-btn:hover {
      background: #e50914;
      color: #fff;
    }
    .nav-menu-dropdown {
      position: absolute;
      right: 0;
      top: 110%;
      background: #fff;
      border: 1px solid #e0e0e0;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.08);
      min-width: 180px;
      z-index: 10;
      display: flex;
      flex-direction: column;
      padding: 0.5rem 0;
    }
    .nav-menu-dropdown a {
      color: #e50914;
      padding: 0.7rem 1.5rem;
      text-decoration: none;
      font-weight: 600;
      cursor: pointer;
      border: none;
      background: none;
      text-align: left;
      transition: background 0.2s, color 0.2s;
    }
    .nav-menu-dropdown a:hover {
      background: #ffeaea;
      color: #b0060f;
    }
  `]
})
export class AppComponent {
  menuAberto = false;
  logado = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.atualizarLogin();
  }

  atualizarLogin() {
    this.logado = !!localStorage.getItem('usuario');
  }

  toggleMenu() {
    this.menuAberto = !this.menuAberto;
    this.atualizarLogin();
  }

  fecharMenu() {
    this.menuAberto = false;
  }

  irParaHome() {
    this.router.navigate(['/']);
  }

  logout() {
    localStorage.removeItem('usuario');
    this.logado = false;
    this.fecharMenu();
    this.router.navigate(['/login']);
  }

  irParaLogin() {
    this.router.navigate(['/login']);
  }

  isLoginPage(): boolean {
    return window.location.pathname === '/login';
  }

  // Método para simular login/logout (para fins de demonstração)
  toggleLogin() {
    this.logado = !this.logado;
  }

  irParaNoticias() {
    if (this.logado) {
      this.router.navigate(['/noticias']);
    }
  }

  irParaConfiguracoes() {
    this.atualizarLogin();
    if (this.logado) {
      this.router.navigate(['/']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
