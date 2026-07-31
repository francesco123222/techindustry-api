package tech.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(

        @NotNull
        Long idUsuario,

        @NotEmpty
        List<OrderItemRequest> itens
) {}
