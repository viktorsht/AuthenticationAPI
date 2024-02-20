package io.github.authapi.controllers;

import io.github.authapi.dto.AuthDTO;
import io.github.authapi.services.AuthenticationService;
import io.github.authapi.services.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDTO authDTO){
        var userAuthentication = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        authenticationManager.authenticate(userAuthentication);
        return authenticationService.makeToken(authDTO);
    }
}
