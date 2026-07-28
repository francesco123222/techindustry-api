package tech.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.global.model.GenericBaseModel;
import tech.model.component.Componente;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_pedido_item", schema = "sch_techindustry")
public class OrderItem extends GenericBaseModel<Long> {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Order pedido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_componente", nullable = false)
    private Componente componente;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
}
