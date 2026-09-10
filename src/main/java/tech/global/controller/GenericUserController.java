package tech.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.global.model.IGenericBaseModel;
import tech.global.service.IUserService;


public class GenericUserController<S extends IUserService<E, T>, E extends IGenericBaseModel, T> {

    @Autowired
    protected S service;

    @GetMapping("/listar_usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Buscar usuário pelo id.")
    public ResponseEntity<E> buscar(@PathVariable T id) {
        E entidade = service.buscarPorId(id);
        return ResponseEntity.ok(entidade);
    }

    @DeleteMapping("/deletar_usuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Deletar usuário pelo id.")
    public ResponseEntity<String> deletar(@PathVariable T id) {
        service.deletarUsuario(id);
        return ResponseEntity.ok("Usuário excluído com sucesso.");
    }
}
