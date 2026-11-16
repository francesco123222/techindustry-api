package tech.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.enums.Priority;
import tech.enums.Status;

import java.time.LocalDate;

public record OrderTrackRequest(

        @NotNull
        Long idPedido,

        @NotBlank
        String titulo,

        @NotBlank
        String descricao,

        @NotNull
        Status status,

        @NotNull
        Priority prioridade,

        @NotNull
        Long idSolicitante,

        @NotBlank
        String nomeSolicitante,

        String atribuidoPorNome,
        String codigoRastreio,
        LocalDate entregaEstimada,
        String notas
) {}
