package tech.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(

        @NotNull
        Long idUsuario,

        @NotEmpty
        @Valid
        List<OrderItemRequest> itens
) {}
