package be.eekhaut.kristof.aws.shop.sales.order.domain.usecase;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.command.PlaceOrderCommand;
import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderLine;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderRepository;
import be.eekhaut.kristof.aws.shop.sales.order.domain.event.EventPublisher;
import be.eekhaut.kristof.aws.shop.sales.order.domain.event.OrderPlacedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Component
public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public PlaceOrderUseCase(OrderRepository orderRepository,
                             EventPublisher eventPublisher) {
        this.orderRepository = requireNonNull(orderRepository);
        this.eventPublisher = requireNonNull(eventPublisher);
    }

    public OrderId execute(PlaceOrderCommand command) {
        OrderId orderId = orderRepository.generateId();
        Order order = createOrder(orderId, command);
        orderRepository.add(order);

        eventPublisher.publish(new OrderPlacedEvent(order));

        return orderId;
    }

    private Order createOrder(OrderId orderId, PlaceOrderCommand command) {
        List<OrderLine> orderLines = createOrderLines(command);
        return new Order(orderId, orderLines);
    }

    private List<OrderLine> createOrderLines(PlaceOrderCommand command) {
        return command.getOrderLines().stream()
                .map(line -> new OrderLine(line.getProductId(), line.getNrOfItems()))
                .collect(toList());
    }
}
