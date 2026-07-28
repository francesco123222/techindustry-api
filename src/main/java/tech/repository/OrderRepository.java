package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.model.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
