package tacos.web.repository;

import tacos.web.entities.Order;

public interface IJdbcOrderRepository {
    Order save(Order order);
}
