package br.com.portalNoticia.services;

import br.com.portalNoticia.dto.CategoriaDto;
import br.com.portalNoticia.entity.Categoria;
import br.com.portalNoticia.repository.CategoriaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria findById(Integer id) throws BadRequestException {
        Optional<Categoria> categoria = repository.findById(id);
        if(categoria.isEmpty()) {
            throw new BadRequestException("Categoria não encontrada");
        }
        return categoria.get();
    }

    public Categoria insert(Categoria categoria) {
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        repository.findById(id);
        repository.deleteById(id);
    }

    public Categoria update(Categoria obj) throws BadRequestException {
        Categoria categoria = findById(obj.getId());
        categoriaUpdate(categoria, obj);
        return repository.save(categoria);
    }

    private void categoriaUpdate(Categoria atual, Categoria novo) {
        atual.setCategoriaPai(novo.getCategoriaPai());
        atual.setDescricao(novo.getDescricao());
        atual.setSubcategorias(novo.getSubcategorias());
    }

    public Categoria fromDto(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setDescricao(dto.getDescricao());
        if (dto.getCategoriaPaiId() != null) {
            Categoria pai = new Categoria();
            pai.setId(dto.getCategoriaPaiId());
            categoria.setCategoriaPai(pai);
        }
        if (dto.getSubcategoriasIds() != null) {
            List<Categoria> subcategorias = dto.getSubcategoriasIds().stream().map(id -> {
                Categoria sub = new Categoria();
                sub.setId(id);
                return sub;
            }).toList();
            categoria.setSubcategorias(subcategorias);
        }
        return categoria;
    }
}
