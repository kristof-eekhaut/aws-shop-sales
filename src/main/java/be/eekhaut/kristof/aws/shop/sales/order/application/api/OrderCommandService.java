package be.eekhaut.kristof.aws.shop.sales.order.application.api;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.command.PlaceOrderCommand;

public interface OrderCommandService {

    String placeOrder(PlaceOrderCommand command);
}
