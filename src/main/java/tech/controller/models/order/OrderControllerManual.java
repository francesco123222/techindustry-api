package tech.controller.models.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dto.order.OrderRequest;
import tech.dto.order.OrderResponse;
import tech.service.models.order.OrderService;

@Tag(name = "Comprar Componentes")
@RestController
@RequestMapping("/pedidos")
public class OrderControllerManual {

    @Autowired
    private OrderService service;

    @PostMapping("/comprar-componente")
    @Operation(description = "Comprar componente.")
    public ResponseEntity<OrderResponse> comprarComponente(
            @RequestBody @Valid OrderRequest request) {

        OrderResponse response = service.incluirPedido(request);

        return ResponseEntity.ok(response);
    }
}
