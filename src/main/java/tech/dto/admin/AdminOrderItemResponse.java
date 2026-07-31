package tech.dto.admin;

import tech.model.order.OrderItem;

import java.math.BigDecimal;

public record AdminOrderItemResponse(
        Long idItem,
        Long idComponente,
        String nomeComponente,
        String tipoComponente,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {

    public AdminOrderItemResponse(OrderItem item) {
        this(
                item.getId(),
                item.getComponente().getId(),
                item.getComponente().getNome(),
                item.getComponente().getTypeComponent().name(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
        );
    }
}
