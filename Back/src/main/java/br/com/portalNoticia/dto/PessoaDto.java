package br.com.portalNoticia.dto;

import br.com.portalNoticia.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PessoaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;

    public PessoaDto(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.email = pessoa.getEmail();
    }

    public PessoaDto(PessoaDto pessoaDto) {
    }

    public static PessoaDto convert(Pessoa pessoa) {
        return PessoaDto.builder()
                .nome(pessoa.getNome())
                .email(pessoa.getEmail())
                .build();
    }
}
