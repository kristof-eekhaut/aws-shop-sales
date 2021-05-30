package be.eekhaut.kristof.aws.shop.sales.order.adapter.event;

import be.eekhaut.kristof.aws.shop.sales.order.domain.event.Event;
import be.eekhaut.kristof.aws.shop.sales.order.domain.event.EventPublisher;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.QueueMessageChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class AmazonSQSEventPublisherImpl implements EventPublisher {

    private static final String QUEUE_NAME = "https://sqs.eu-west-3.amazonaws.com/441536988167/testQueue";
    public static final int MESSAGE_TIMEOUT_IN_MS = 30000;

    private final AmazonSQSAsync amazonSqs;
    private final ObjectMapper objectMapper;

    public AmazonSQSEventPublisherImpl(AmazonSQSAsync amazonSqs,
                                       ObjectMapper objectMapper) {
        this.amazonSqs = requireNonNull(amazonSqs);
        this.objectMapper = requireNonNull(objectMapper);
    }

    @Override
    public void publish(Event event) {
        MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, QUEUE_NAME);
        String payload = createPayload(event);

        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader("messageType", event.getMessageType())
                .build();

        boolean isMessageSent = messageChannel.send(message, MESSAGE_TIMEOUT_IN_MS);
        if (!isMessageSent)
            throw new EventPublishingException("Message could not be sent to queue: " + QUEUE_NAME);
    }

    private String createPayload(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new EventPublishingException("Could not serialize Event to a String.", exception);
        }
    }
}
