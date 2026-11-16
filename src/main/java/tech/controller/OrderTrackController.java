package tech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dto.order.OrderTrackResponse;
import tech.service.order.OrderTrackService;

import java.util.List;

@Tag(name = "Acompanhamento de Pedidos")
@RestController
@RequestMapping("/api/acompanhar-pedidos")
@RequiredArgsConstructor
public class OrderTrackController {

    private final OrderTrackService orderTrackService;

    @GetMapping
    @Operation(summary = "Listar todos os pedidos em andamento.")
    public ResponseEntity<List<OrderTrackResponse>> acompanharPedidos() {

        return ResponseEntity.ok(orderTrackService.listarPedidosAndamento());
    }

    @GetMapping("/{idPedido}")
    @Operation(description = "Buscar acompanhamento pelo id do pedido.")
    public ResponseEntity<OrderTrackResponse> acompanharPedidoId(@PathVariable Long idPedido) {

        return ResponseEntity.ok(orderTrackService.buscarPorId(idPedido));
    }
}
