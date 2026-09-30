package tech.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.config.core.UserDetailsImpl;
import tech.model.User;
import tech.repository.UserRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UserRepository userRepository;

    public AutenticacaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));

       return new UserDetailsImpl(user);
    }
}
