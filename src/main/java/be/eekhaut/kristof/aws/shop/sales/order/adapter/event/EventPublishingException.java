package be.eekhaut.kristof.aws.shop.sales.order.adapter.event;

public class EventPublishingException extends RuntimeException {

    EventPublishingException(String message) {
        super(message);
    }

    EventPublishingException(String message, Throwable cause) {
        super(message, cause);
    }
}
