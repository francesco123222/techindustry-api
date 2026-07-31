package tech.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(

        @NotNull
        Long idComponente,

        @NotNull
        @Min(1)
        Integer quantidade
) {}
