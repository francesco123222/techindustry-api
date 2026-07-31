package tech.dto.order;

import tech.model.order.OrderItem;
import tech.model.component.enums.TypeComponent;

public record OrderItemResponse(
        Long id,
        Long idComponente,
        String nomeComponente,
        TypeComponent tipoComponente,
        Integer quantidade
) {

    public OrderItemResponse(OrderItem item) {
        this(
                item.getId(),
                item.getComponente().getId(),
                item.getComponente().getNome(),
                item.getComponente().getTypeComponent(),
                item.getQuantidade()
        );
    }
}
