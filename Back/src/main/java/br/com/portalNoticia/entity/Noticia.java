package br.com.portalNoticia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String conteudo;
    private LocalDate dataPublicacao;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Autor autor;

    @Column(nullable = false)
    private boolean destaque;
    private String imagemUrl; // Caminho/URL da imagem
    private String status; // "RASCUNHO" ou "PUBLICADA"

    public Noticia(Integer id, String titulo, String conteudo, LocalDate dataPublicacao, Categoria categoria, Autor autor, boolean destaque, String imagemUrl, String status) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
        this.destaque = destaque;
        this.imagemUrl = imagemUrl;
        this.status = status;
    }

    public Noticia() {
        // Construtor padrão necessário para JPA e uso em serviços
    }

    public boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
