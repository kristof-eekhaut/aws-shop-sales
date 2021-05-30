package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller;

import be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation.OrderRepresentation;
import be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation.OrderRepresentationAssembler;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.OrderCommandService;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.OrderQueryService;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.command.PlaceOrderCommand;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/orders", produces = { MediaTypes.HAL_JSON_VALUE })
public class OrderController {

    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;
    private final OrderRepresentationAssembler orderRepresentationAssembler;

    public OrderController(OrderQueryService orderQueryService,
                           OrderCommandService orderCommandService,
                           OrderRepresentationAssembler orderRepresentationAssembler) {
        this.orderQueryService = requireNonNull(orderQueryService);
        this.orderCommandService = requireNonNull(orderCommandService);
        this.orderRepresentationAssembler = requireNonNull(orderRepresentationAssembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRepresentation> getOrderById(@PathVariable("id") String id) {

        Optional<OrderView> order = orderQueryService.getOrderById(id);

        if (order.isEmpty())
            return ResponseEntity.notFound().build();

        OrderRepresentation representation = orderRepresentationAssembler.toModel(order.get());
        return ResponseEntity.ok(representation);
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestBody PlaceOrderCommand command) {
        String orderId = orderCommandService.placeOrder(command);

        return ResponseEntity.accepted()
                .location(linkTo(methodOn(OrderController.class).getOrderById(orderId)).toUri())
                .build();
    }
}
