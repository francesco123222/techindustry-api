package tech.dto.admin;

import tech.model.order.OrderItem;

import java.math.BigDecimal;

public record AdminOrderItemResponse(
        Long idItem,
        Long idComponente,
        String nomeComponente,
        String tipoComponente,
        Integer quantidade,
        String precoUnitario,
        String subtotal
) {

    public AdminOrderItemResponse(OrderItem item) {
        this(
                item.getId(),
                item.getComponente().getId(),
                item.getComponente().getNome(),
                item.getComponente().getTypeComponent().name(),
                item.getQuantidade(),
                String.format("R$ %.2f", item.getPrecoUnitario()),
                String.format("R$ %.2f", item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
        );
    }
}
