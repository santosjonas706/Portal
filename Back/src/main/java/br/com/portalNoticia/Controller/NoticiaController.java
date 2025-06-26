package br.com.portalNoticia.Controller;

import br.com.portalNoticia.dto.NoticiaDto;
import br.com.portalNoticia.entity.Noticia;
import br.com.portalNoticia.services.NoticiaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/noticia")
public class NoticiaController {

    private final NoticiaService service;

    @Autowired
    public NoticiaController(NoticiaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NoticiaDto>> findAll() {
        List<Noticia> list = service.findAll();
        List<NoticiaDto> listDto = list.stream().map(NoticiaDto::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NoticiaDto> findById(@PathVariable Long id) throws BadRequestException {
        Noticia noticia = service.findById(id);
        return ResponseEntity.ok().body(new NoticiaDto(noticia));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody NoticiaDto dto, @PathVariable Long id) throws BadRequestException {
        dto.setId(id.intValue());
        Noticia noticia = service.fromDto(dto);
        noticia = service.update(noticia);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<NoticiaDto> insert(@RequestBody NoticiaDto dto) {
        Noticia noticia = service.fromDto(dto);
        noticia = service.insert(noticia);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(noticia.getId()).toUri();
        return ResponseEntity.created(uri).body(new NoticiaDto(noticia));
    }

    @PostMapping("/upload-imagem")
    public ResponseEntity<String> uploadImagem(@RequestParam("file") MultipartFile file) {
        try {
            String pastaUpload = "uploads/";
            Path pastaPath = Paths.get(pastaUpload);
            if (!Files.exists(pastaPath)) {
                Files.createDirectories(pastaPath);
            }
            String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path caminhoArquivo = pastaPath.resolve(nomeArquivo);
            Files.copy(file.getInputStream(), caminhoArquivo);
            String url = "/uploads/" + nomeArquivo;
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload da imagem");
        }
    }

    @GetMapping("/publicas")
    public List<NoticiaDto> listarPublicadas() {
        return service.findByStatus("PUBLICADA")
            .stream()
            .map(NoticiaDto::new)
            .collect(Collectors.toList());
    }
}
