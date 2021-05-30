package be.eekhaut.kristof.aws.shop.sales.order.domain.event;

import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class OrderPlacedEvent implements Event {

    public static final String MESSAGE_TYPE = "order-placed";

    private final String orderId;
    private final List<OrderLine> orderLines;

    public OrderPlacedEvent(Order order) {
        this(order.getId().getValue(),
                mapOrderLines(order));
    }

    public OrderPlacedEvent(String orderId, List<OrderLine> orderLines) {
        this.orderId = requireNonNull(orderId);
        this.orderLines = requireNonNull(orderLines);
    }

    @Override
    public String getMessageType() {
        return MESSAGE_TYPE;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    private static List<OrderLine> mapOrderLines(Order order) {
        return order.getOrderLines().stream()
                .map(orderLine -> new OrderLine(orderLine.getProductId(), orderLine.getNrOfItems()))
                .collect(toList());
    }

    public static class OrderLine {
        private final String productId;
        private final int nrOfItems;

        public OrderLine(String productId, int nrOfItems) {
            this.productId = requireNonNull(productId);
            this.nrOfItems = nrOfItems;
        }

        public String getProductId() {
            return productId;
        }

        public int getNrOfItems() {
            return nrOfItems;
        }
    }
}
