package tech.dto;

import tech.model.AreaComponent;
import tech.model.Componente;
import tech.model.TypeComponent;

import java.math.BigDecimal;

public record ComponenteResponse(
    Long id,
    String nome,
    AreaComponent areaComponent,
    TypeComponent typeComponent,
    String descricao,
    BigDecimal preco
) {

    public ComponenteResponse(Componente componente) {
        this(
                componente.getId(),
                componente.getNome(),
                componente.getAreaComponent(),
                componente.getTypeComponent(),
                componente.getDescricao(),
                componente.getPreco()
        );
    }
}
