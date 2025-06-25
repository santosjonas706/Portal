package br.com.portalNoticia.Controller;

import br.com.portalNoticia.dto.UsuarioDto;
import br.com.portalNoticia.entity.Usuario;
import br.com.portalNoticia.services.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll() {
        List<Usuario> list = service.findAll();
        List<UsuarioDto> listDto = list.stream().map(UsuarioDto::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable Integer id) throws BadRequestException {
        Usuario usuario = service.findById(id);
        return ResponseEntity.ok().body(new UsuarioDto(usuario));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UsuarioDto dto) {
        Usuario usuario = service.fromDto(dto);
        usuario = (Usuario) service.insert(usuario);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> update(@RequestBody UsuarioDto dto, @PathVariable Integer id) throws BadRequestException {
        dto.setId(id);
        Usuario usuario = service.fromDto(dto);
        usuario = service.update(usuario);
        return ResponseEntity.noContent().build();
    }
}
