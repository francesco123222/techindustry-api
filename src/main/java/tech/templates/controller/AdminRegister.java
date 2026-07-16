package tech.templates.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.dto.user.AdminRequest;
import tech.dto.user.UserRequest;
import tech.dto.user.UserResponse;
import tech.model.user.enums.UserRole;
import tech.service.user.UserService;
import tech.utils.user.ValidadorCPF;

@Tag(name = "Cadastrar Admnistradores")
@Controller
@RequestMapping("/api")
public class AdminRegister {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidadorCPF validadorCPF;

    @GetMapping("/main_admin")
    @Operation(description = "Página de cadastro principal.")
    public String siteAdmin(Model model) {

        model.addAttribute("adminRequest", new AdminRequest("", "", ""));
        return "register";
    }

    @PostMapping("/register_admin")
    @Operation(description = "Cadastrar Administrador.")
    public String cadastrarAdmin(@ModelAttribute("adminRequest") @Valid UserRequest request,
                                 BindingResult result) {

        if (request.cpf() == null || request.cpf().isBlank()) {
            result.rejectValue("cpf", "FieldError", "O CPF não pode estar em branco.");
        } else {
            if (!ValidadorCPF.isValido(request.cpf())) {
                result.rejectValue("cpf", "FieldError", "Por favor, insira um CPF válido.");
            } else {
                if (validadorCPF.cpfExiste(request.cpf())) {
                    result.rejectValue("cpf", "FieldError", "CPF já cadastrado no sistema.");
                }
            }
        }

        if (validadorCPF.adminExiste(request.usuario())) {
            result.rejectValue("usuario", "FieldError", "Admin já cadastrado no sistema.");
        }

        if (result.hasErrors()) {
                return "register";
        }

            userService.cadastrarUsuarioAdmin(request, UserRole.ADMIN);
            return "redirect:/api/main_admin?sucesso=true";
        }
}
