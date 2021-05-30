package be.eekhaut.kristof.aws.shop.sales.order.application.api.command;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class PlaceOrderCommand {

    private final List<OrderLine> orderLines;

    @JsonCreator
    public PlaceOrderCommand(List<OrderLine> orderLines) {
        this.orderLines = requireNonNull(orderLines);
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class OrderLine {
        private final String productId;
        private final int nrOfItems;

        @JsonCreator
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

    public static final class Builder {
        private final List<OrderLine> orderLines = new ArrayList<>();

        private Builder() {
        }

        public Builder orderLine(String productId, int nrOfItems) {
            this.orderLines.add(new OrderLine(productId, nrOfItems));
            return this;
        }

        public PlaceOrderCommand build() {
            return new PlaceOrderCommand(orderLines);
        }
    }
}
