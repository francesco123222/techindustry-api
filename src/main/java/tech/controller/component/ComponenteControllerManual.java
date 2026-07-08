package tech.controller.component;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tech.dto.component.ComponenteRequest;
import tech.dto.component.ComponenteResponse;
import tech.global.controller.GenericComponenteRequestController;
import tech.model.component.Componente;
import tech.service.component.ComponenteService;
import tech.utils.component.DatabaseUtils;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Cadastrar Componentes")
@RestController
@RequestMapping("/api")
public class ComponenteControllerManual extends GenericComponenteRequestController<ComponenteService, Componente, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/cadastrar-componente")
    @Operation(description = "Cadastrar componente.")
    public ResponseEntity<ComponenteResponse> cadastrarComponente(
            @RequestBody @Valid ComponenteRequest request) {

       ComponenteResponse response = service.incluir(request);

       return ResponseEntity.ok(response);
    }

    @GetMapping("/componentes")
    @Operation(description = "Listar componentes.")
    public ResponseEntity<List<ComponenteResponse>> listarComponentes() {

        List<Componente> componentes = DatabaseUtils.listarComponentes(jdbcTemplate);

        List<ComponenteResponse> response = componentes.stream()
                .map(ComponenteResponse::new)
                .collect(Collectors.toList());


        return ResponseEntity.ok(response);

    }

    @GetMapping("/componente/{id}")
    @Operation(description = "Retornar componente pelo id.")
    public ComponenteResponse retornarComponente(@PathVariable Long id) {

        return service.buscarporId(id);
    }

    @GetMapping("/componentes/buscar")
    @Operation(description = "Buscar componente pelo nome.")
    public ResponseEntity<List<ComponenteResponse>> buscarPorComponente(@RequestParam String nome) {
        List<ComponenteResponse> response = service.buscarPorNome(nome);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(description = "Atualizar componente.")
    public ResponseEntity<ComponenteResponse> atualizarComponente(@PathVariable Long id, @RequestBody @Valid ComponenteRequest request) {
        ComponenteResponse response = service.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(description = "Deletar componente.")
    public ResponseEntity<?> deletarComponente(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
