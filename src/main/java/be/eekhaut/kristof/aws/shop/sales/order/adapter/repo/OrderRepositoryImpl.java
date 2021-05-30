package be.eekhaut.kristof.aws.shop.sales.order.adapter.repo;

import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderInMemoryStorage orderInMemoryStorage;

    public OrderRepositoryImpl(OrderInMemoryStorage orderInMemoryStorage) {
        this.orderInMemoryStorage = requireNonNull(orderInMemoryStorage);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderInMemoryStorage.get(orderId.getValue());
    }
}
