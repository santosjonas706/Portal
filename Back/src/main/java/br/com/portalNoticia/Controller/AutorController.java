package br.com.portalNoticia.Controller;

import br.com.portalNoticia.Controller.util.Url;
import br.com.portalNoticia.dto.AutorDto;
import br.com.portalNoticia.entity.Autor;
import br.com.portalNoticia.services.AutorService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

    private final AutorService service;

    @Autowired
    public AutorController(AutorService autorService) {
        this.service = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorDto>> findAll() {
        List<Autor> list = service.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        List<AutorDto> listDto = list.stream().map(AutorDto::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AutorDto>> findByNome(@RequestParam(value = "nome", defaultValue = "") String nome) {
        String nomeDecodificado = Url.decodeParam(nome);
        List<Autor> list = service.findByNome(nomeDecodificado);
        List<AutorDto> listDto = list.stream().map(AutorDto::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorDto> findById(@PathVariable Integer id) throws BadRequestException {
        Autor autor = service.findById(id);
        return ResponseEntity.ok(new AutorDto(autor));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AutorDto> insert(@RequestBody AutorDto dto) {
        Autor autor = service.fromDto(dto);
        autor = service.insert(autor);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(url).body(new AutorDto(autor));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody AutorDto dto, @PathVariable Integer id) throws BadRequestException {
        dto.setId(id);
        Autor autor = service.fromDto(dto);
        service.update(autor);
        return ResponseEntity.noContent().build();
    }
}
