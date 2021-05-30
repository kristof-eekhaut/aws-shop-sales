package be.eekhaut.kristof.aws.shop.sales.order.domain;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Order implements OrderView {

    private final OrderId id;
    private final List<OrderLine> orderLines;

    public Order(OrderId id, List<OrderLine> orderLines) {
        this.id = requireNonNull(id);
        this.orderLines = requireNonNull(orderLines);
    }

    @Override
    public OrderId getId() {
        return id;
    }

    @Override
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
}
