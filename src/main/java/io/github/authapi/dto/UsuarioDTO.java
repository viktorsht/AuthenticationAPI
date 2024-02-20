package io.github.authapi.dto;

import io.github.authapi.enums.RoleEnum;

public record UsuarioDTO(String name, String login, String password, RoleEnum role) {
}
