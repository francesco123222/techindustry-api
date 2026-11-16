package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.dto.order.OrderTrackResponse;
import tech.model.OrderTrack;

import java.util.Optional;

public interface OrderTrackRepository extends JpaRepository<OrderTrack, Long> {

    Optional<OrderTrack> findByPedidoId(Long idPedido);
}
