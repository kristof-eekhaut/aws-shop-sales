package be.eekhaut.kristof.aws.shop.sales.order.adapter.repo;

import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId.orderId;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

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

    @Override
    public OrderId generateId() {
        return orderId(randomUUID().toString());
    }

    @Override
    public void add(Order order) {
        orderInMemoryStorage.persist(order.getId().getValue(), order);
    }
}
