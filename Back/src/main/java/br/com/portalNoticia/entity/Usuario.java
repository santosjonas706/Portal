package br.com.portalNoticia.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Usuario extends Pessoa {

    private String login;
    private String senha;

}
