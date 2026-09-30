package tech.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.dto.AdminOrderResponse;
import tech.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public List<AdminOrderResponse> listarPedidos() {

        return orderRepository.buscarPedidosComDetalhes()
                .stream()
                .map(AdminOrderResponse::new)
                .toList();
    }
}
