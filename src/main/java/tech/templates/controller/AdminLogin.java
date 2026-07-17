package tech.templates.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.dto.user.LoginRequest;


@Tag(name = "Logar Admnistradores")
@Controller
@RequestMapping("/api")
public class AdminLogin {

    @GetMapping("/main_login")
    @Operation(description = "Página de login administradores.")
    public String siteloginAdmin(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String accessDenied,
                                 Model model) {

        model.addAttribute("adminRequest", new LoginRequest("", ""));

        if (error != null) {
            model.addAttribute("erro",
                    "Usuário ou senha inválidos");
        }
        if (accessDenied != null) {
            model.addAttribute("erro", "Acesso negado: Somente papel como administrador.");
        }

        return "login";
    }

}
