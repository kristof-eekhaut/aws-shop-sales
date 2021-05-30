package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller;

import be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation.OrderRepresentation;
import be.eekhaut.kristof.aws.shop.sales.order.adapter.controller.representation.OrderRepresentationAssembler;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.OrderQueryService;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.view.OrderView;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(value = "/orders", produces = { MediaTypes.HAL_JSON_VALUE })
public class OrderController {

    private final OrderQueryService orderQueryService;
    private final OrderRepresentationAssembler orderRepresentationAssembler;

    public OrderController(OrderQueryService orderQueryService,
                           OrderRepresentationAssembler orderRepresentationAssembler) {
        this.orderQueryService = requireNonNull(orderQueryService);
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
}
