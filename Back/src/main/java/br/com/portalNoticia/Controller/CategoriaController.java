package br.com.portalNoticia.Controller;

import br.com.portalNoticia.dto.CategoriaDto;
import br.com.portalNoticia.entity.Categoria;
import br.com.portalNoticia.services.CategoriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @Autowired
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> findAll() {
        List<Categoria> list = service.findAll();
        List<CategoriaDto> listDto = list.stream().map(CategoriaDto::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDto> findById(@PathVariable Integer id) throws BadRequestException {
        Categoria categoria = service.findById(id);
        return ResponseEntity.ok().body(new CategoriaDto(categoria));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody CategoriaDto dto, @PathVariable Integer id) throws BadRequestException {
        dto.setId(id);
        Categoria categoria = service.fromDto(dto);
        categoria = service.update(categoria);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> insert(@RequestBody CategoriaDto dto) {
        Categoria categoria = service.fromDto(dto);
        categoria = service.insert(categoria);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(url).body(new CategoriaDto(categoria));
    }
}
