package br.com.portalNoticia.Controller;

import br.com.portalNoticia.dto.PessoaDto;
import br.com.portalNoticia.services.PessoaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {

    private final PessoaService service;

    @Autowired
    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDto>> findAll() {
        List<PessoaDto> listDto = service.findAll();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDto> findById(@PathVariable Integer id) throws BadRequestException {
        PessoaDto pessoa = service.findById(id);
        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaDto> update(@RequestBody PessoaDto dto, @PathVariable Integer id) throws BadRequestException {
        PessoaDto pessoa = service.update(dto, id);
        return ResponseEntity.ok(pessoa);
    }

    @PostMapping
    public ResponseEntity<PessoaDto> insert(@RequestBody PessoaDto dto){
        PessoaDto pessoa = service.insert(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(url).body(pessoa);
    }
}
