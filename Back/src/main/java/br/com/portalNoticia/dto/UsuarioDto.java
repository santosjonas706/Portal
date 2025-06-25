package br.com.portalNoticia.dto;

import br.com.portalNoticia.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class UsuarioDto extends PessoaDto {

    @Serial
    private static final long serialVersionUID = 1L;

    private String login;

    public UsuarioDto(Usuario usuario) {
        super(usuario);
        this.login = usuario.getLogin();
    }

}
