import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Noticia {
  id: number;
  titulo: string;
  conteudo: string;
  dataPublicacao: string;
  categoria?: { descricao: string };
  autor?: { nome: string };
  imagemUrl?: string;
}

interface CategoriaComNoticias {
  descricao: string;
  noticias: Noticia[];
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  noticias: Noticia[] = [];
  categorias: CategoriaComNoticias[] = [];
  carregando = true;
  abaSelecionada: 'noticias' | 'categorias' = 'noticias';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/noticia').subscribe({
      next: data => {
        // Ajuste: usar os dados reais do backend
        this.noticias = data.map(n => ({
          ...n,
          categoria: { descricao: n.categoriaNome },
          autor: { nome: n.autorNome }
        }));
        this.carregando = false;
      },
      error: () => {
        this.noticias = [];
        this.carregando = false;
      }
    });
  }

  imagemPorIndice(idx: number): string {
    const imagens = [
      'https://cdn.cnn.com/cnnnext/dam/assets/240606110858-01-apple-wwdc-2024-super-tease.jpg',
      'https://cdn.cnn.com/cnnnext/dam/assets/240606110858-02-apple-wwdc-2024-super-tease.jpg',
      'https://cdn.cnn.com/cnnnext/dam/assets/240606110858-03-apple-wwdc-2024-super-tease.jpg',
      'https://cdn.cnn.com/cnnnext/dam/assets/240606110858-04-apple-wwdc-2024-super-tease.jpg',
      'https://cdn.cnn.com/cnnnext/dam/assets/240606110858-05-apple-wwdc-2024-super-tease.jpg'
    ];
    return imagens[idx % imagens.length];
  }

  noticiaImagem(noticia: Noticia): string {
    return noticia.imagemUrl ? `url('${noticia.imagemUrl}')` : 'none';
  }

  preencherCategoriasFicticias() {
    this.categorias = [
      {
        descricao: 'Tecnologia',
        noticias: [
          {
            id: 10,
            titulo: 'Novo chip revoluciona smartphones',
            conteudo: 'Processadores mais rápidos chegam ao mercado.',
            dataPublicacao: '2025-06-09',
            categoria: { descricao: 'Tecnologia' },
            autor: { nome: 'Tech News' },
            imagemUrl: this.imagemPorIndice(0)
          },
          {
            id: 11,
            titulo: 'Robôs no cotidiano',
            conteudo: 'Robôs domésticos ganham espaço nas casas brasileiras.',
            dataPublicacao: '2025-06-08',
            categoria: { descricao: 'Tecnologia' },
            autor: { nome: 'Robótica' },
            imagemUrl: this.imagemPorIndice(1)
          }
        ]
      },
      {
        descricao: 'Esportes',
        noticias: [
          {
            id: 20,
            titulo: 'Final emocionante no futebol',
            conteudo: 'Jogo termina nos pênaltis e emociona torcida.',
            dataPublicacao: '2025-06-07',
            categoria: { descricao: 'Esportes' },
            autor: { nome: 'Esportes' },
            imagemUrl: this.imagemPorIndice(2)
          }
        ]
      },
      {
        descricao: 'Economia',
        noticias: [
          {
            id: 30,
            titulo: 'Bolsa de valores bate recorde',
            conteudo: 'Mercado financeiro em alta histórica.',
            dataPublicacao: '2025-06-06',
            categoria: { descricao: 'Economia' },
            autor: { nome: 'Economia' },
            imagemUrl: this.imagemPorIndice(3)
          }
        ]
      }
    ];
  }

  selecionarAba(aba: 'noticias' | 'categorias') {
    this.abaSelecionada = aba;
  }
}
