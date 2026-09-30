package tech.dto.component;

import tech.enums.AreaComponent;
import tech.model.Componente;
import tech.enums.TypeComponent;

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