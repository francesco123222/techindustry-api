package tech.controller.security;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import tech.model.user.enums.UserRole;
import tech.service.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthControllerAdmin {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthControllerAdmin(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.usuario(), request.senha());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User adminlogado = (User) authentication.getPrincipal();

            if (adminlogado.getRole() != UserRole.ADMIN) {
                return ResponseEntity.status(403).body("Acesso negado: Usuário não é admnistrador.");
            }

            String tokenJwt = tokenService.gerarToken(adminlogado);
            String role = adminlogado.getRole().name();

            return ResponseEntity.ok(new TokenResponse(tokenJwt, role));

        } catch (Exception e) {
            System.out.println("ERRO REAL NO LOGIN: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Erro de autenticação "+ e.getMessage());
        }
    }
}
