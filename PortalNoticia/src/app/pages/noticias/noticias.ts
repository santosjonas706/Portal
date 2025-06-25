import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CategoriaService, Categoria } from '../../core/categoria.service';

interface Noticia {
  id?: number;
  titulo: string;
  conteudo: string;
  dataPublicacao: string;
  categoriaId?: number;
  autorId?: number;
  imagemUrl?: string;
  status?: string;
}

@Component({
  selector: 'app-noticias',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './noticias.html',
  styleUrls: ['./noticias.css']
})
export class Noticias {
  noticias: Noticia[] = [];
  categorias: Categoria[] = [];
  novaNoticia: Noticia = { titulo: '', conteudo: '', dataPublicacao: '', imagemUrl: '' };
  imagemSelecionada: File | null = null;
  imagemPreview: string | null = null;
  carregando = false;
  erro = '';
  logado = false;
  usuario = '';
  senha = '';

  constructor(private http: HttpClient, private categoriaService: CategoriaService) {}

  ngOnInit() {
    this.atualizarLogin();
    if (this.logado) {
      this.buscarNoticias();
    }
    this.carregando = true;
    this.categoriaService.getAll().subscribe({
      next: data => {
        this.categorias = data;
        this.carregando = false;
      },
      error: () => {
        this.categorias = [];
        this.carregando = false;
      }
    });
  }

  buscarNoticias() {
    this.carregando = true;
    this.http.get<Noticia[]>('http://localhost:8080/noticia').subscribe({
      next: data => {
        this.noticias = data;
        this.carregando = false;
      },
      error: () => {
        this.erro = 'Erro ao buscar notícias';
        this.carregando = false;
      }
    });
  }

  onFileSelected(event: any) {
    this.imagemSelecionada = event.target.files[0];
    if (this.imagemSelecionada) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagemPreview = e.target.result;
      };
      reader.readAsDataURL(this.imagemSelecionada);
    } else {
      this.imagemPreview = null;
    }
  }

  uploadImagem(): Promise<string> {
    if (!this.imagemSelecionada) return Promise.resolve('');
    const formData = new FormData();
    formData.append('file', this.imagemSelecionada);
    return this.http.post('http://localhost:8080/noticia/upload-imagem', formData, { responseType: 'text' })
      .toPromise()
      .then(url => url ?? '');
  }

  async adicionarNoticia() {
    this.carregando = true;
    this.erro = '';
    let sucesso = false;
    try {
      let imagemUrl = '';
      if (this.imagemSelecionada) {
        imagemUrl = await this.uploadImagem();
      }
      // Ajuste: garantir status e ids corretos
      const noticiaParaSalvar = {
        ...this.novaNoticia,
        imagemUrl,
        autorId: Number(this.novaNoticia.autorId) || 1, // garante número
        categoriaId: Number(this.novaNoticia.categoriaId) || 1, // garante número
        destaque: false,
        status: this.novaNoticia.status || 'RASCUNHO'
      };
      console.log('Dados enviados:', noticiaParaSalvar);
      await this.http.post('http://localhost:8080/noticia', noticiaParaSalvar).toPromise();
      this.novaNoticia = { titulo: '', conteudo: '', dataPublicacao: '', imagemUrl: '' };
      this.imagemSelecionada = null;
      this.imagemPreview = null;
      this.buscarNoticias();
      sucesso = true;
    } catch (e) {
      console.log(e);
      this.erro = 'Erro ao adicionar notícia';
    }
    this.carregando = false;
    if (sucesso) {
      setTimeout(() => { this.erro = 'Notícia adicionada com sucesso!'; setTimeout(() => this.erro = '', 2000); }, 100);
    }
  }

  removerNoticia(id?: number) {
    if (!id) return;
    this.carregando = true;
    this.http.delete('http://localhost:8080/noticia/' + id).subscribe({
      next: () => this.buscarNoticias(),
      error: () => {
        this.erro = 'Erro ao remover notícia';
        this.carregando = false;
      }
    });
  }

  loginSimples(usuario: string, senha: string) {
    // Simulação: só permite admin/1234
    if (usuario === 'admin' && senha === '1234') {
      localStorage.setItem('usuario', usuario);
      this.logado = true;
      this.buscarNoticias();
    } else {
      this.erro = 'Usuário ou senha inválidos';
    }
  }

  logout() {
    localStorage.removeItem('usuario');
    this.logado = false;
  }

  atualizarLogin() {
    const usuario = localStorage.getItem('usuario');
    this.logado = !!usuario;
  }

  getCategoriaDescricao(id?: number): string {
    if (!id) return '';
    const cat = this.categorias.find(c => c.id === id);
    return cat ? cat.descricao : '';
  }

  getImagemUrl(imagemUrl?: string): string {
    if (!imagemUrl) return '';
    if (imagemUrl.startsWith('http')) return imagemUrl;
    // Corrige para buscar do backend
    return 'http://localhost:8080' + imagemUrl;
  }

  // Ao editar notícia, se status for 'PUBLICADA', desabilite o campo categoria
  // Removido: uso de this.form pois não existe Reactive Form
  desabilitarCampoCategoriaAoEditar(status: string) {
    // Função não faz nada pois não há FormGroup
  }
}
