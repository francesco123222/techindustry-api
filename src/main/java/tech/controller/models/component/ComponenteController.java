package tech.controller.models.component;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.dto.component.ComponenteRequest;
import tech.dto.component.ComponenteResponse;
import tech.global.controller.GenericComponenteController;
import tech.model.component.Componente;
import tech.service.models.component.ComponenteService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Componentes")
@RestController
@RequestMapping("/api")
@Validated
public class ComponenteController extends GenericComponenteController<ComponenteService, Componente, Long> {

    @PostMapping("/cadastrar_componente")
    @Operation(description = "Cadastrar componente.")
    public ResponseEntity<ComponenteResponse> cadastrarComponente(
            @RequestBody @Valid ComponenteRequest request) {

        ComponenteResponse response = service.incluir(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar_componentes")
    @Operation(description = "Listar componentes.")
    public ResponseEntity<List<ComponenteResponse>> listarComponentes() {

        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/componentes/buscar")
    @Operation(description = "Buscar componente pelo nome.")
    public ResponseEntity<List<ComponenteResponse>> buscarPorComponente(
            @RequestParam(value = "nome", required = false) List<String> nome) {

        if (nome == null || nome.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<String> nomesValidos = nome.stream()
                .filter(n -> n != null && !n.trim().isEmpty())
                .collect(Collectors.toList());

        if (nomesValidos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ComponenteResponse> response = service.buscarPorNome(nomesValidos);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar_componente/{id}")
    @Operation(description = "Atualizar um componente existente")
    public ResponseEntity<ComponenteResponse> atualizarComponente(@PathVariable Long id, @RequestBody @Valid ComponenteRequest request) {
        ComponenteResponse response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

}
