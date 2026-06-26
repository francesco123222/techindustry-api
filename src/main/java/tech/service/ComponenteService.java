package tech.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.dto.ComponenteRequest;
import tech.dto.ComponenteResponse;
import tech.model.Componente;
import tech.repository.ComponenteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponenteService {

    private final ComponenteRepository repository;

    @Transactional
    public ComponenteResponse salvar(ComponenteRequest request) {
        Componente componente = Componente.builder()
                .nome(request.nome())
                .areaComponent(request.areaComponent())
                .typeComponent(request.typeComponent())
                .descricao(request.descricao())
                .preco(request.preco())
                .build();

        Componente componenteSalvo  = repository.save(componente);

        return new ComponenteResponse(componenteSalvo);
    }

    @Transactional(readOnly = true)
    public List<ComponenteResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ComponenteResponse::new)
                .collect(Collectors.toList());
    }

}
