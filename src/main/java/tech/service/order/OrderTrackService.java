package tech.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.dto.order.OrderTrackResponse;
import tech.handler.exception.ResourceNotFoundException;
import tech.repository.OrderTrackRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderTrackService {

    private final OrderTrackRepository orderTrackRepository;

    public List<OrderTrackResponse> listarPedidosAndamento() {

        return orderTrackRepository.findAll()
                .stream()
                .map(OrderTrackResponse::new)
                .toList();
    }

    public OrderTrackResponse buscarPorId(Long id) {

        return orderTrackRepository.findByPedidoId(id)
                .map(OrderTrackResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido (pendente) com ID " + id + " não existe na nossa base de dados."
                ));
    }

}
