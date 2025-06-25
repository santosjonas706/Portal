package br.com.portalNoticia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Autor extends Pessoa {

    private String biografia;
    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Noticia> noticias;
}
