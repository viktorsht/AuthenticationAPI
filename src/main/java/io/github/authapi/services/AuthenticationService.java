package io.github.authapi.services;

import io.github.authapi.dto.AuthDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    public String makeToken(AuthDTO authDTO);
    public String validateToken(String token);
}
