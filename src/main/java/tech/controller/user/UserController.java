package tech.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.dto.user.UserRequest;
import tech.dto.user.UserResponse;
import tech.global.controller.GenericUserController;
import tech.model.user.User;
import tech.model.user.enums.UserRole;
import tech.service.user.UserService;

import java.util.List;

@Tag(name = "Cadastrar Usuários")
@RestController
@RequestMapping("/api")
public class UserController extends GenericUserController<UserService, User, Long> {

    @PostMapping("/cadastrar_usuario")
    @Operation(description = "Cadastrar usuário.")
    public ResponseEntity<UserResponse> cadastrarUsuario(
            @RequestBody @Valid UserRequest request) {

        UserResponse response = service.cadastrarUsuario(request, UserRole.USER);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar_admin")
    @Operation(description = "Cadastrar usuário administrador.")
    public ResponseEntity<UserResponse> cadastrarAdmin(
            @RequestBody @Valid UserRequest request) {

        UserResponse response = service.cadastrarUsuarioAdmin(request, UserRole.ADMIN);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar_usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Listar usuários.")
    public ResponseEntity<List<UserResponse>> listarUsuarios() {

        return ResponseEntity.ok(service.listarUsuarios());
    }
}
