package be.eekhaut.kristof.aws.shop.sales.order.domain;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(OrderId orderId);
}
