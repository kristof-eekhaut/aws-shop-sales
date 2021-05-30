package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class OrderRepresentation extends RepresentationModel<OrderRepresentation> {

    private final String id;
    private final List<OrderLine> orderLines;

    private OrderRepresentation(Builder builder) {
        id = builder.id;
        orderLines = builder.orderLines;
    }

    public String getId() {
        return id;
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

        private OrderLine(String productId, int nrOfItems) {
            this.productId = productId;
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
        private String id;
        private List<OrderLine> orderLines = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder orderLine(String productId, int nrOfItems) {
            this.orderLines.add(new OrderLine(productId, nrOfItems));
            return this;
        }

        public OrderRepresentation build() {
            return new OrderRepresentation(this);
        }
    }
}
