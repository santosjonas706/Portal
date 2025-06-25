package br.com.portalNoticia.repository;

import br.com.portalNoticia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByLoginAndSenha(String login, String senha);
}
