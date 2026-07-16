package tech.templates.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.dto.user.LoginRequest;
import tech.model.user.User;
import tech.model.user.enums.UserRole;
import tech.service.security.TokenService;


@Tag(name = "Logar Admnistradores")
@Controller
@RequestMapping("/api")
public class AdminLogin {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AdminLogin(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping("/main_login")
    @Operation(description = "Página de login administradores.")
    public String siteloginAdmin(Model model) {

        model.addAttribute("adminRequest", new LoginRequest("", ""));
        return "login";
    }

    @PostMapping("/auth/admin")
    @Operation(description = "Logar Administrador.")
    public String logarAdmin(@ModelAttribute("adminRequest") @Valid LoginRequest request,
                             BindingResult result) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(request.usuario(), request.senha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User adminlogado = (User) authentication.getPrincipal();

        if (adminlogado.getRole() != UserRole.ADMIN) {
            result.rejectValue("usuario", "FieldError", "Acesso negado: Somente papel como administradores.");

            return "login";
        }

        String tokenJwt = tokenService.gerarToken(adminlogado);
        String role = adminlogado.getRole().name();

        return "redirect:/api/main?token=" + tokenJwt + "&role=" + role;
    }
}
