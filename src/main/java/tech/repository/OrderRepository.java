package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.model.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT DISTINCT o 
        FROM Order o
        JOIN FETCH o.titular
        JOIN FETCH o.itens i
        JOIN FETCH i.componente
    """)
    List<Order> buscarPedidosComDetalhes();
}
