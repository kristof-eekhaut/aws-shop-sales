package be.eekhaut.kristof.aws.shop.sales.order.application.service;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.OrderCommandService;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.command.PlaceOrderCommand;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId;
import be.eekhaut.kristof.aws.shop.sales.order.domain.usecase.PlaceOrderUseCase;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final PlaceOrderUseCase placeOrderUseCase;

    public OrderCommandServiceImpl(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = requireNonNull(placeOrderUseCase);
    }

    @Override
    public String placeOrder(PlaceOrderCommand command) {
        OrderId orderId = placeOrderUseCase.execute(command);
        return orderId.getValue();
    }
}
