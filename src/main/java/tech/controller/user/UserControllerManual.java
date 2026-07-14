package tech.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.dto.user.UserRequest;
import tech.dto.user.UserResponse;
import tech.global.controller.GenericUserController;
import tech.model.user.User;
import tech.model.user.enums.UserRole;
import tech.service.user.UserService;
import tech.utils.user.DatabaseUtils;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Cadastrar Usuários")
@RestController
@RequestMapping("/api")
public class UserControllerManual extends GenericUserController<UserService, User, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/cadastrar-usuario")
    @Operation(description = "Cadastrar usuário.")
    public ResponseEntity<UserResponse> cadastrarUsuario(
            @RequestBody @Valid UserRequest request) {

        UserResponse response = service.cadastrarUsuario(request, UserRole.USER);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar-admin")
    @Operation(description = "Cadastrar usuário administrador.")
    public ResponseEntity<UserResponse> cadastrarAdmin(
            @RequestBody @Valid UserRequest request) {

        UserResponse response = service.cadastrarUsuarioAdmin(request, UserRole.ADMIN);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Listar usuários.")
    public ResponseEntity<List<UserResponse>> listarUsuarios() {

        List<User> usuarios = DatabaseUtils.listarUsuarios(jdbcTemplate);

        List<UserResponse> response = usuarios.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Buscar usuário pelo id.")
    public UserResponse retornarUsuario(@PathVariable Long id) {

        return service.buscarporId(id);
    }

    @PutMapping("/atualizar-usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Atualizar usuário pelo id.")
    public ResponseEntity<UserResponse> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        UserResponse response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar-usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Deletar usuário pelo id.")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.ok("Usuário excluído com sucesso.");
    }

}
