package tech.config.core;

import tech.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.enums.UserRole;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return user.getUsuario();
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
