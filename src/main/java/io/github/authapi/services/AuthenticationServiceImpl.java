package io.github.authapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.authapi.dto.AuthDTO;
import io.github.authapi.models.Usuario;
import io.github.authapi.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UsuarioRepository usuarioRepository;

    public AuthenticationServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public String makeToken(AuthDTO authDTO) {
        Usuario user = usuarioRepository.findByLogin(authDTO.login());
        return geraTokenJwt(user);
    }

    private Algorithm makeAlgorithm(){
        return Algorithm.HMAC256("my-secret");
    }

    public String geraTokenJwt(Usuario user){
        try {
            Algorithm algorithm = makeAlgorithm();
            return JWT.create()
                    .withIssuer("authapi")
                    .withSubject(user.getLogin())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        }
        catch (JWTCreationException e){
            throw  new RuntimeException("Erro ao gerar o token: " + e.getMessage());

        }
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(1)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = makeAlgorithm();
            return JWT.require(algorithm)
                    .withIssuer("authapi")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e){
            return "Token invalid";
        }
    }
}
