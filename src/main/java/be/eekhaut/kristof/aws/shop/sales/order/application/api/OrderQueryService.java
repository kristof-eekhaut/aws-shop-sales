package be.eekhaut.kristof.aws.shop.sales.order.application.api;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;

import java.util.Optional;

public interface OrderQueryService {

    Optional<OrderView> getOrderById(String id);
}
