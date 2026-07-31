package tech.dto.admin;

import tech.model.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AdminOrderResponse(
        Long idPedido,
        LocalDateTime dataPedido,
        Long idUsuario,
        String nomeUsuario,
        BigDecimal totalPedido,
        List<AdminOrderItemResponse> itens
) {

    public AdminOrderResponse(Order order) {
        this(
                order.getId(),
                order.getDataPedido(),
                order.getTitular().getId(),
                order.getTitular().getUsuario(),
                order.getItens()
                        .stream()
                        .map(item -> item.getPrecoUnitario()
                                .multiply(BigDecimal.valueOf(item.getQuantidade())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                order.getItens()
                        .stream()
                        .map(AdminOrderItemResponse::new)
                        .toList()
        );
    }
}
