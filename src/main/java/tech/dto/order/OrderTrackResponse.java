package tech.dto.order;

import tech.enums.Priority;
import tech.enums.Status;
import tech.model.OrderTrack;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderTrackResponse(
        Long id,
        Long idPedido,
        String titulo,
        String descricao,
        Status status,
        Priority prioridade,
        Long idSolicitante,
        String nomeSolicitante,
        String atribuidoPorNome,
        String codigoRastreio,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        LocalDate entregaEstimada,
        LocalDateTime entregadoEm,
        String notas
) {

    public OrderTrackResponse(OrderTrack track) {
        this(
                track.getId(),
                track.getPedido() != null ? track.getPedido().getId() : null,
                track.getTitulo(),
                track.getDescricao(),
                track.getStatus(),
                track.getPrioridade(),
                track.getIdSolicitante(),
                track.getNomeSolicitante(),
                track.getAtribuidoPorNome(),
                track.getCodigoRastreio(),
                track.getCriadoEm(),
                track.getAtualizadoEm(),
                track.getEntregaEstimada(),
                track.getEntregadoEm(),
                track.getNotas()
        );
    }
}
