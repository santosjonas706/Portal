package br.com.portalNoticia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Posicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}