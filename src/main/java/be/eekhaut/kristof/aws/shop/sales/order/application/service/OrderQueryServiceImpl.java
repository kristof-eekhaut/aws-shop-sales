package be.eekhaut.kristof.aws.shop.sales.order.application.service;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.OrderQueryService;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId.orderId;
import static java.util.Objects.requireNonNull;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = requireNonNull(orderRepository);
    }

    @Override
    public Optional<OrderView> getOrderById(String id) {
        return orderRepository.findById(orderId(id))
                .map(OrderView.class::cast);
    }
}
