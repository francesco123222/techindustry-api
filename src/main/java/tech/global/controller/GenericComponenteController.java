package tech.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tech.global.model.IGenericBaseModel;
import tech.global.service.IComponenteService;

public class GenericComponenteController<S extends IComponenteService<E, T>, E extends IGenericBaseModel, T> {

    @Autowired
    protected S service;

    @GetMapping("/retornar_componente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Buscar componente pelo id.")
    public ResponseEntity<E> buscar(@PathVariable T id) {
        E entidade = service.buscarPorId(id);
        return ResponseEntity.ok(entidade);
    }

    @DeleteMapping("/deletar_componente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Deletar componente pelo id.")
    public ResponseEntity<String> deletar(@PathVariable T id) {
        service.deletarComponente(id);
        return ResponseEntity.ok("Componente excluído com sucesso.");
    }
}
