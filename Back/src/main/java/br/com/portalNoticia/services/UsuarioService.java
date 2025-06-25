package br.com.portalNoticia.services;

import br.com.portalNoticia.dto.UsuarioDto;
import br.com.portalNoticia.entity.Pessoa;
import br.com.portalNoticia.entity.Usuario;
import br.com.portalNoticia.repository.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository repository;

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public Usuario findById(Integer id) throws BadRequestException {
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possivel encontrar o usuario com ID: " + id);
        }
        return usuario.get();
    }

    public Pessoa insert(Usuario usuario) {
        return repository.save(usuario);
    }

    public void delete(Integer id) throws BadRequestException{
        findById(id);
        repository.deleteById(id);
    }

    public Usuario update(Usuario obj) throws BadRequestException {
        Usuario usuario = findById(obj.getId());
        updateUsuario(usuario, obj);
        return repository.save(usuario);
    }

    private void updateUsuario(Usuario novoUsuario, Usuario usuario) {
        novoUsuario.setLogin(usuario.getLogin());
    }

    public Usuario fromDto(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setLogin(dto.getLogin());
        return usuario;
    }
}
