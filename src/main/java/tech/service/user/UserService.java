package tech.service.user;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.users.GenericUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tech.dto.user.UserRequest;
import tech.dto.user.UserResponse;
import tech.global.service.GenericUserService;
import tech.global.service.IUserService;
import tech.handler.exception.BusinessException;
import tech.handler.exception.ResourceNotFoundException;
import tech.model.user.User;
import tech.model.user.enums.UserRole;
import tech.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends GenericUserService<UserRepository, User, Long> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse cadastrarUsuario(UserRequest request, UserRole role) {
        validarCadastroUnico(request.usuario(), request.cpf());

        User user =  User.builder()
                .usuario(request.usuario())
                .cpf(request.cpf())
                .senha(request.senha())
                .role(role)
                .build();

        User userSalvo = this.salvarUsuario(user);
        return new UserResponse(userSalvo);
    }

    @Transactional
    public UserResponse cadastrarUsuarioAdmin(UserRequest request, UserRole role) {
        validarCadastroUnico(request.usuario(), request.cpf());

        User user = User.builder()
                .usuario(request.usuario())
                .cpf(request.cpf())
                .senha(request.senha())
                .role(role)
                .build();

        User userSalvo = this.salvarUsuario(user);
        return new UserResponse(userSalvo);
    }

    @Transactional
    public User salvarUsuario(User usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> listarUsuarios() {
        return repository.listarUsuariosCustom()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse buscarporId(Long id) {
        return repository.findById(id)
                .map(UserResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário com ID " + id + " não existe no nosso banco de dados."
                ));
    }

    @Transactional
    public UserResponse atualizar(Long id, UserRequest userRequest) {
        User userExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        validarAtualizacaoUnica(id, userRequest.usuario(), userRequest.cpf());

        userExistente.setUsuario(userRequest.usuario());
        userExistente.setCpf(userRequest.cpf());
        userExistente.setSenha(passwordEncoder.encode(userRequest.senha()));

        User userAtualizado = this.atualizar(id, userExistente);
        return new UserResponse(userAtualizado);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Registro não encontrado para exclusão com o ID: " + id);
        }

        repository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES DE VALIDAÇÃO (REGRAS DE NEGÓCIO) ---

    private void validarCadastroUnico(String usuario, String cpf) {
        if (repository.existsByUsuario(usuario)) {
            throw new BusinessException("O nome de usuário '" + usuario + "' já está em uso.");
        }

        if (cpf != null) {
            String cpfBusca = cpf.trim();
            // Se o usuário digitou o CPF limpo (11 dígitos), formata com pontos e traço antes de checar o banco
            if (cpfBusca.matches("^\\d{11}")) {
                cpfBusca = String.format("%s.%s.%s-%s",
                        cpfBusca.substring(0, 3),
                        cpfBusca.substring(3, 6),
                        cpfBusca.substring(6, 9),
                        cpfBusca.substring(9, 11)
                );
            }

            if (repository.existsByCpf(cpfBusca)) {
                throw new BusinessException("O CPF informado já está cadastrado no sistema.");
            }
        }
    }

    private void validarAtualizacaoUnica(Long id, String usuario, String cpf) {
        repository.findByUsuario(usuario).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new BusinessException("O nome de usuário '" + usuario + "' já está em uso por outro cadastro.");
            }
        });

        repository.findByCpf(cpf).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new BusinessException("O CPF informado já está cadastrado em outro usuário.");
            }
        });
    }

}
