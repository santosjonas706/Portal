package br.com.portalNoticia.repository;

import br.com.portalNoticia.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
