package br.com.portalNoticia.dto;

import br.com.portalNoticia.entity.Autor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AutorDto extends PessoaDto{

    @Serial
    private static final long serialVersionUID = 1L;

    private String biografia;
    private List<NoticiaResumoDto> noticias;

    public AutorDto(Autor autor) {
        super(autor);
        this.biografia = autor.getBiografia();
        if (autor.getNoticias() != null) {
            this.noticias = autor.getNoticias()
                    .stream()
                    .map(NoticiaResumoDto::new)
                    .collect(Collectors.toList());
        }
    }
}
