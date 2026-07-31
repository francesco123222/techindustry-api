package tech.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.global.model.GenericBaseModel;
import tech.model.user.User;
import java.util.List;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_pedido", schema = "sch_techindustry")
public class Order extends GenericBaseModel<Long> {

    @NotNull
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User titular;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itens;

    @PrePersist
    protected void onCreate() {
        this.dataPedido = LocalDateTime.now();
    }
}
