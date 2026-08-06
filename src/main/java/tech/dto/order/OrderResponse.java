package tech.dto.order;

import tech.model.order.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String dataPedido,
        Long idUsuario,
        List<OrderItemResponse> itens
) {

    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getDataPedido(),
                order.getCliente().getId(),
                order.getItens()
                        .stream()
                        .map(OrderItemResponse::new)
                        .toList()
        );
    }
}