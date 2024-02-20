package io.github.authapi.services;

import io.github.authapi.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO);

    public List<UsuarioDTO> listar();
}
