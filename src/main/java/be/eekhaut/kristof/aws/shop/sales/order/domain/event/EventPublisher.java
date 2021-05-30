package be.eekhaut.kristof.aws.shop.sales.order.domain.event;

public interface EventPublisher {

    void publish(Event event);
}
