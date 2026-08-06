package tech.dto.admin;

import tech.model.order.Order;

import java.math.BigDecimal;
import java.util.List;

public record AdminOrderResponse(
        Long idPedido,
        String dataPedido,
        Long idCliente,
        String nomeCliente,
        String totalPedido,
        List<AdminOrderItemResponse> itens
) {

    public AdminOrderResponse(Order order) {
        this(
                order.getId(),
                order.getDataPedido(),
                order.getCliente().getId(),
                order.getCliente().getUsuario(),
                String.format("R$ %.2f", order.getItens()
                        .stream()
                        .map(item -> item.getPrecoUnitario()
                                .multiply(BigDecimal.valueOf(item.getQuantidade())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)),
                order.getItens()
                        .stream()
                        .map(AdminOrderItemResponse::new)
                        .toList()
        );
    }
}
