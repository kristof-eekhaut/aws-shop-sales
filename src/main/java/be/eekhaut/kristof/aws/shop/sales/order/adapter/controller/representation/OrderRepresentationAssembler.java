package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderLineView;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderRepresentationAssembler implements RepresentationModelAssembler<OrderView, OrderRepresentation> {

    @Override
    public OrderRepresentation toModel(OrderView entity) {
        OrderRepresentation.Builder representationBuilder = OrderRepresentation.builder()
                .id(entity.getId().getValue());

        entity.getOrderLines()
                .forEach(orderLine -> mapOrderLine(representationBuilder, orderLine));

        return representationBuilder.build();
    }

    private void mapOrderLine(OrderRepresentation.Builder representationBuilder, OrderLineView entity) {
        representationBuilder.orderLine(entity.getProductId(), entity.getNrOfItems());
    }
}
