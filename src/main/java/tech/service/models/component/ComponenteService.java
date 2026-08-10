package tech.service.models.component;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tech.dto.component.ComponenteRequest;
import tech.dto.component.ComponenteResponse;
import tech.global.service.GenericComponenteService;
import tech.global.service.IComponentService;
import tech.model.component.Componente;
import tech.repository.ComponenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ComponenteService extends GenericComponenteService<ComponenteRepository, Componente, Long> implements IComponentService {

    @Transactional
    public ComponenteResponse incluir(ComponenteRequest request) {
        Componente componente = Componente.builder()
                .nome(request.nome())
                .areaComponent(request.areaComponent())
                .typeComponent(request.typeComponent())
                .descricao(request.descricao())
                .preco(request.preco())
                .build();

        Componente componenteSalvo  = this.incluir(componente);

        return new ComponenteResponse(componenteSalvo);
    }

    @Transactional(readOnly = true)
    public List<ComponenteResponse> listarTodos() {
        return repository.listarComponentesCustom()
                .stream()
                .map(ComponenteResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComponenteResponse buscarporId(Long id) {
        return repository.findById(id)
                .map(componente -> new ComponenteResponse(componente))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Componente com ID " + id + " não existe."
                ));
    }

    @Transactional(readOnly = true)
    public List<ComponenteResponse> buscarPorNome(List<String> nomes) {
        if (nomes == null || nomes.isEmpty()) {
            return List.of();
        }

        List<Componente> todosComponentes = new ArrayList<>();

        for (String nome : nomes) {
            if (nome != null && !nome.trim().isEmpty()) {
                List<Componente> encontrados = repository.findByNomeContainingIgnoreCase(nome.trim());
                todosComponentes.addAll(encontrados);
            }
        }

        List<Componente> componentesSemDuplicados = todosComponentes.stream()
                .distinct()
                .collect(Collectors.toList());

        if (componentesSemDuplicados.isEmpty()) {
            return List.of();
        }

        return componentesSemDuplicados.stream()
                .map(ComponenteResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ComponenteResponse atualizar(Long id, ComponenteRequest componenteRequest) {
        Componente componenteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Componente não encontrado com o ID: " + id));

        componenteExistente.setNome(componenteRequest.nome());
        componenteExistente.setAreaComponent(componenteRequest.areaComponent());
        componenteExistente.setTypeComponent(componenteRequest.typeComponent());
        componenteExistente.setPreco(componenteRequest.preco());
        componenteExistente.setDescricao(componenteRequest.descricao());

        Componente componenteAtualizado = this.atualizar(id, componenteExistente);

        return new ComponenteResponse(componenteAtualizado);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado para exclusão com o ID: " + id);
        }

        repository.deleteById(id);
    }

}
