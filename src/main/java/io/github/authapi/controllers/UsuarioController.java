package io.github.authapi.controllers;

import io.github.authapi.dto.UsuarioDTO;
import io.github.authapi.services.UsuarioService;
import io.github.authapi.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    private UsuarioDTO salvar(@RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("/admin")
    private String list(){
        return "SÓ ADM PODE ACESSAR";
    }


    @GetMapping("/user")
    private String listuser(){
        return "SÓ USER PODE ACESSAR";
    }

    @GetMapping("/admin/listAll")
    private List<UsuarioDTO> listAllUser(){
        return usuarioService.listar();
    }
}
