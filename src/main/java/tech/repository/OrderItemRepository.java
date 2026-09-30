package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
