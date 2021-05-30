package be.eekhaut.kristof.aws.shop.sales.order.domain;

import static java.util.Objects.requireNonNull;

public class OrderId {

    private final String value;

    public static OrderId orderId(String value) {
        return new OrderId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderId orderId = (OrderId) o;

        return value != null ? value.equals(orderId.value) : orderId.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    private OrderId(String value) {
        this.value = requireNonNull(value);
    }
}
