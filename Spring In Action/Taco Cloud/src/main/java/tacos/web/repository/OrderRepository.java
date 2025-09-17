package tacos.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.web.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
