package br.com.portalNoticia.dto;

import br.com.portalNoticia.entity.Categoria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class CategoriaDto {

    private Integer id;
    private String descricao;
    private Integer categoriaPaiId;
    private List<Integer> subcategoriasIds;

    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.descricao = categoria.getDescricao();

        if(categoria.getCategoriaPai() != null) {
            this.categoriaPaiId = categoria.getCategoriaPai().getId();
        }

        if(categoria.getSubcategorias() != null) {
            this.subcategoriasIds = categoria.getSubcategorias().stream().map(Categoria::getId).toList();
        }
    }

}
