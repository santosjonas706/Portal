package br.com.portalNoticia.services;

import br.com.portalNoticia.dto.NoticiaDto;
import br.com.portalNoticia.entity.Autor;
import br.com.portalNoticia.entity.Categoria;
import br.com.portalNoticia.entity.Noticia;
import br.com.portalNoticia.repository.NoticiaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AutorService autorService;

    public List<Noticia> findAll() {
        return repository.findAll();
    }

    public Noticia findById(Long id) throws BadRequestException {
        Optional<Noticia> noticia = repository.findById(id);
        if(noticia.isEmpty()) {
            throw new BadRequestException("Noticia não encontrada");
        }
        return noticia.get();
    }

    public Noticia insert(Noticia noticia) {
        return repository.save(noticia);
    }

    public void delete(Long id) throws BadRequestException {
        findById(id);
        repository.deleteById(id);
    }

    public Noticia update (Noticia obj) throws BadRequestException{
        Noticia noticia = findById(obj.getId().longValue());
        noticiaUpdate(noticia, obj);
        return repository.save(noticia);
    }

    private void noticiaUpdate(Noticia antigo, Noticia novo) {
        antigo.setAutor(novo.getAutor());
        antigo.setCategoria(novo.getCategoria());
        antigo.setTitulo(novo.getTitulo());
        antigo.setConteudo(novo.getConteudo());
        antigo.setDataPublicacao(novo.getDataPublicacao());
        antigo.setDestaque(novo.isDestaque());
        antigo.setImagemUrl(novo.getImagemUrl());
    }

    public Noticia fromDto(NoticiaDto dto) {
        Noticia noticia = new Noticia();
        noticia.setId(dto.getId());
        noticia.setTitulo(dto.getTitulo());
        noticia.setConteudo(dto.getConteudo());
        noticia.setDataPublicacao(dto.getDataPublicacao());
        noticia.setDestaque(dto.isDestaque());
        noticia.setImagemUrl(dto.getImagemUrl());
        noticia.setStatus(dto.getStatus()); // garantir que status seja copiado
        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            try {
                categoria = categoriaService.findById(dto.getCategoriaId().intValue());
            } catch (Exception e) {
                throw new RuntimeException("Categoria não encontrada para o ID: " + dto.getCategoriaId());
            }
        }
        noticia.setCategoria(categoria);
        Autor autor = null;
        if (dto.getAutorId() != null) {
            try {
                autor = autorService.findById(dto.getAutorId());
            } catch (Exception e) {
                throw new RuntimeException("Autor não encontrado para o ID: " + dto.getAutorId());
            }
        }
        noticia.setAutor(autor);
        return noticia;
    }

    public List<Noticia> findByStatus(String status) {
        return repository.findByStatus(status);
    }
}
