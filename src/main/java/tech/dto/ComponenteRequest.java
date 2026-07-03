package tech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.model.AreaComponent;
import tech.model.TypeComponent;

import java.math.BigDecimal;

public record ComponenteRequest(
   @NotNull(message = "O nome é obrigatório")
   @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
   String nome,

   @NotNull(message = "A área é obrigatória")
   AreaComponent areaComponent,

   @NotNull(message = "O tipo é obrigatório")
   TypeComponent typeComponent,

   @NotNull(message = "A descrição é obrigatória")
   @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres")
   String descricao,

   @NotNull(message = "O preço é obrigatório")
   BigDecimal preco
) {}
