package br.com.portalNoticia.Controller;

import br.com.portalNoticia.entity.Publicidade;
import br.com.portalNoticia.entity.Posicao;
import br.com.portalNoticia.entity.Categoria;
import br.com.portalNoticia.repository.PublicidadeRepository;
import br.com.portalNoticia.repository.PosicaoRepository;
import br.com.portalNoticia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publicidade")
public class PublicidadeController {

    private final PublicidadeRepository repository;
    private final PosicaoRepository posicaoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public PublicidadeController(PublicidadeRepository repository, PosicaoRepository posicaoRepository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.posicaoRepository = posicaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/ativas-vigentes")
    public List<Publicidade> getAtivasVigentes() {
        return repository.findAtivosEVigentes(LocalDate.now());
    }

    @GetMapping("/por-posicao/{posicaoId}")
    public ResponseEntity<List<Publicidade>> getPorPosicao(@PathVariable Long posicaoId) {
        Optional<Posicao> posicaoOpt = posicaoRepository.findById(posicaoId);
        if (posicaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findByPosicao(posicaoOpt.get()));
    }

    @GetMapping("/por-categoria/{categoriaId}")
    public ResponseEntity<List<Publicidade>> getPorCategoria(@PathVariable Integer categoriaId) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findByCategorias(categoriaOpt.get()));
    }
}