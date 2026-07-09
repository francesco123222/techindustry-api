package tech.controller.security;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dto.user.LoginRequest;
import tech.dto.user.TokenResponse;
import tech.model.user.User;
import tech.service.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.usuario(), request.senha());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User userLogado = (User) authentication.getPrincipal();
            String tokenJwt = tokenService.gerarToken(userLogado);
            String role = userLogado.getRole().name();

            return ResponseEntity.ok(new TokenResponse(tokenJwt, role));
        } catch (Exception e) {
            System.out.println("ERRO REAL NO LOGIN: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Erro de autenticação " + e.getMessage());
        }
    }
}
