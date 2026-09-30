package tech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import tech.global.model.GenericBaseModel;
import tech.enums.UserRole;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_usuario", schema = "sch_techindustry")
@EntityListeners(UserListener.class)
public class User extends GenericBaseModel<Long> {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(max = 40)
    @Column(name = "usuario", length = 40, nullable = false)
    private String usuario;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(
            regexp = "(^\\d{11}$)|(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)",
            message = "O CPF deve conter 11 números ou estar no formato 000.000.000-00"
    )
    @Column(nullable = false, unique = true, name = "cpf", length = 14)
    private String cpf;

    @NotNull
    @Size(min = 6, max = 100, message = "A sua senha deve ter entre 6 e 100 caracteres")
    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    // Novo campo adicionado para o Enum
    @NotNull(message = "O perfil do usuário é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 30, nullable = false)
    private UserRole role;
}