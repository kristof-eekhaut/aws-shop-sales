package be.eekhaut.kristof.aws.shop.sales.order.application.api.view;

import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId;

import java.util.List;

public interface OrderView {

    OrderId getId();
    List<? extends OrderLineView> getOrderLines();
}
