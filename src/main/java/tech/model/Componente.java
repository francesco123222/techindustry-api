package tech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import tech.global.model.GenericBaseModel;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_componente", schema = "sch_techindustry")
public class Componente extends GenericBaseModel<Long> {

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50)
    private String nome;

    @NotNull
    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    private AreaComponent areaComponent;

    @NotNull
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TypeComponent typeComponent;

    @NotNull
    @Size(max = 150)
    @Column(name = "descricao", length = 150)
    private String descricao;

    @NotNull
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

}
