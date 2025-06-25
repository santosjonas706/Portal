package br.com.portalNoticia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Publicidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String imagemUrl;
    private String linkDestino;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;
    private String tipoMidia; // imagem, video, script, etc.
    private Integer prioridade;
    private Integer visualizacoes = 0;
    private Integer clicks = 0;

    @ManyToOne
    @JoinColumn(name = "posicao_id")
    private Posicao posicao;

    @ManyToMany
    @JoinTable(
        name = "publicidade_categoria",
        joinColumns = @JoinColumn(name = "publicidade_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;
}