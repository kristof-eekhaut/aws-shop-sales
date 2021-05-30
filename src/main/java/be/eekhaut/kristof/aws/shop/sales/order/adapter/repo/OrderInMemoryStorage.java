package be.eekhaut.kristof.aws.shop.sales.order.adapter.repo;

import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderInMemoryStorage {

    private final Map<String, Order> ORDER_MAP = new LinkedHashMap<>();

    public List<Order> getAll() {
        return new ArrayList<>(ORDER_MAP.values());
    }

    public Optional<Order> get(String key) {
        return Optional.ofNullable(ORDER_MAP.get(key));
    }

    public void persist(String key, Order order) {
        ORDER_MAP.put(key, order);
    }

    public void clear() {
        ORDER_MAP.clear();
    }
}
