package io.github.authapi.services;

import io.github.authapi.dto.UsuarioDTO;
import io.github.authapi.models.Usuario;
import io.github.authapi.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    final private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario userExist = usuarioRepository.findByLogin(usuarioDTO.login());
        if(userExist != null){
            throw new RuntimeException("Usuário já existe");
        }
        var passwordHash = passwordEncoder.encode(usuarioDTO.password());
        Usuario entity = new Usuario(usuarioDTO.name(), usuarioDTO.login(), passwordHash, usuarioDTO.role());
        Usuario newUser = usuarioRepository.save(entity);
        return new UsuarioDTO(newUser.getName(), newUser.getLogin(), newUser.getPassword(), newUser.getRole());
    }
}
