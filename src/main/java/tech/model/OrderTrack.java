package tech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.enums.Priority;
import tech.enums.Status;
import tech.global.model.GenericBaseModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_acompanhar_pedidos", schema = "sch_techindustry")
public class OrderTrack extends GenericBaseModel<Long> {

    @NotNull
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", unique = true)
    private Order pedido;

    @NotNull
    @Column(name = "titulo")
    private String titulo;

    @NotNull
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private Priority prioridade;

    @NotNull
    @Column(name = "id_solicitante")
    private Long idSolicitante;

    @NotNull
    @Column(name = "nome_solicitante")
    private String nomeSolicitante;

    @Column(name = "atribuido_por_nome")
    private String atribuidoPorNome;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "entrega_estimada")
    private LocalDate entregaEstimada;

    @Column(name = "entregado_em")
    private LocalDateTime entregadoEm;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
}
