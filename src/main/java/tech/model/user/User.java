package tech.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.global.model.GenericBaseModel;
import tech.model.user.enums.UserRole;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_usuario", schema = "sch_techindustry")
@EntityListeners(UserListener.class)
public class User extends GenericBaseModel<Long> implements UserDetails {

    @NotNull
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



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta ativa e não expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta não bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Senha válida e não expirada
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário habilitado
    }
}