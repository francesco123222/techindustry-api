package tech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.dto.ComponenteRequest;
import tech.dto.ComponenteResponse;
import tech.global.controller.GenericComponenteRequestController;
import tech.model.Componente;
import tech.service.ComponenteService;

import java.util.List;

@Tag(name = "Cadastrar Componentes")
@RestController
@RequestMapping("/api")
@Validated
public class ComponenteController extends GenericComponenteRequestController<ComponenteService, Componente, Long> {

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

    @GetMapping("/retornar_componente/{id}")
    @Operation(description = "Retornar componente pelo id.")
    public ComponenteResponse retornarComponente(@PathVariable Long id) {

        return service.buscarporId(id);
    }

    @PutMapping("/atualizar_componente/{id}")
    @Operation(description = "Atualizar um componente existente")
    public ResponseEntity<ComponenteResponse> atualizarComponente(@PathVariable Long id, @RequestBody @Valid ComponenteRequest request) {
        ComponenteResponse response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar_componente/{id}")
    @Operation(description = "Deletar um componente existente")
    public ResponseEntity<String> deletarComponente(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.ok("Componente excluído com sucesso");
    }

}
