package be.eekhaut.kristof.aws.shop.sales.order.domain;

import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderLineView;

import static java.util.Objects.requireNonNull;

public class OrderLine implements OrderLineView {

    private final String productId;
    private final int nrOfItems;

    public OrderLine(String productId, int nrOfItems) {
        this.productId = requireNonNull(productId);
        this.nrOfItems = nrOfItems;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public int getNrOfItems() {
        return nrOfItems;
    }
}
