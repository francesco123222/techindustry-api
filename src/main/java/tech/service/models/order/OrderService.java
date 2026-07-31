package tech.service.models.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.dto.order.OrderItemRequest;
import tech.dto.order.OrderRequest;
import tech.dto.order.OrderResponse;
import tech.model.component.Componente;
import tech.model.order.Order;
import tech.model.order.OrderItem;
import tech.model.user.User;
import tech.repository.ComponenteRepository;
import tech.repository.OrderRepository;
import tech.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ComponenteRepository componenteRepository;

    @Transactional
    public OrderResponse incluirPedido(OrderRequest request) {

        User usuario = userRepository.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Order order = Order.builder()
                .titular(usuario)
                .build();

        List<OrderItem> itens = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.itens()) {

            Componente componente = componenteRepository.findById(itemRequest.idComponente())
                    .orElseThrow(() -> new RuntimeException("Componente não encontrado"));

            OrderItem item = OrderItem.builder()
                    .pedido(order)
                    .componente(componente)
                    .quantidade(itemRequest.quantidade())
                    .precoUnitario(componente.getPreco())
                    .build();

            itens.add(item);
        }

        order.setItens(itens);

        Order pedidoSalvo = orderRepository.save(order);

        return new OrderResponse(pedidoSalvo);
    }
}
