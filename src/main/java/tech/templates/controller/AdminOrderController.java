package tech.templates.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.dto.admin.AdminOrderResponse;
import tech.service.models.order.AdminOrderService;

import java.util.List;

@Tag(name = "Listar Pedidos")
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService service;

    @GetMapping("/pedidos")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminOrderResponse>> listarPedidos(){

        return ResponseEntity.ok(service.listarPedidos());
    }
}
