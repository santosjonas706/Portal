package br.com.portalNoticia.services;

import br.com.portalNoticia.dto.AutorDto;
import br.com.portalNoticia.entity.Autor;
import br.com.portalNoticia.repository.AutorRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository repository;

    public List<Autor> findAll() {
        return repository.findAll();
    }

    public Autor findById(Integer id) throws BadRequestException {
        Optional<Autor> autor = repository.findById(id);
        if(autor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possivel encontrar o autor com ID: " + id);
        }
        return autor.get();
    }

    public Autor insert(Autor autor) {
        return repository.save(autor);
    }

    public void delete(Integer id) throws BadRequestException {
        findById(id);
        repository.deleteById(id);
    }

    public void update(Autor obj) throws BadRequestException {
        Autor autor = findById(obj.getId());
        autorUpdate(obj);
        repository.save(autor);
    }

    private void autorUpdate(Autor atual) {
        Autor novo = new Autor();
        atual.setBiografia(novo.getBiografia());
    }
    public Autor fromDto(AutorDto autorDto) {
        Autor autor = new Autor();
        autor.setId(autorDto.getId());
        autor.setNome(autorDto.getNome());
        autor.setEmail(autorDto.getEmail());
        autor.setBiografia(autorDto.getBiografia());
        return autor;
    }

    public List<Autor> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
}
