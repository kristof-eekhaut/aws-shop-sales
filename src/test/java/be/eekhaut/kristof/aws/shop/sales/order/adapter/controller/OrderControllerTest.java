package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller;

import be.eekhaut.kristof.aws.shop.sales.order.adapter.repo.OrderInMemoryStorage;
import be.eekhaut.kristof.aws.shop.sales.order.application.api.command.PlaceOrderCommand;
import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderLine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId.orderId;
import static be.eekhaut.kristof.aws.shop.sales.order.test.TestConstants.BASE_URL;
import static be.eekhaut.kristof.aws.shop.sales.order.test.TestConstants.UUID_PATTERN;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderInMemoryStorage orderInMemoryStorage;

    @BeforeEach
    void setUp() {
        orderInMemoryStorage.clear();

        orderInMemoryStorage.persist("7", new Order(orderId("7"), asList(
                new OrderLine("3", 6),
                new OrderLine("4", 8)
        )));
    }

    @Test
    void shouldReturnOrderById() throws Exception {

        mockMvc.perform(get("/orders/7"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("id").value(7))
                .andExpect(jsonPath("orderLines", hasSize(2)))

                .andExpect(jsonPath("orderLines[0].productId").value(3))
                .andExpect(jsonPath("orderLines[0].nrOfItems").value(6))

                .andExpect(jsonPath("orderLines[1].productId").value(4))
                .andExpect(jsonPath("orderLines[1].nrOfItems").value(8));
    }

    @Test
    void canPlaceOrder() throws Exception {
        PlaceOrderCommand placeOrderCommand = PlaceOrderCommand.builder()
                .orderLine("3", 2)
                .orderLine("5", 1)
                .build();

        String content = objectMapper.writeValueAsString(placeOrderCommand);
        MvcResult mvcResult = mockMvc.perform(post("/orders")
                .contentType(MediaTypes.HAL_JSON)
                .content(content)
        )
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(header().string(LOCATION, matchesPattern(BASE_URL + "/" + UUID_PATTERN)))
                .andReturn();

        String id = mvcResult.getResponse().getHeader(LOCATION).split(BASE_URL + "/")[1];

        Optional<Order> foundOrder = orderInMemoryStorage.get(id);
        assertThat(foundOrder).isPresent();

        Order order = foundOrder.get();
        assertThat(order.getId()).isEqualTo(orderId(id));
        assertThat(order.getOrderLines()).hasSize(2);

        assertThat(order.getOrderLines().get(0).getProductId()).isEqualTo("3");
        assertThat(order.getOrderLines().get(0).getNrOfItems()).isEqualTo(2);

        assertThat(order.getOrderLines().get(1).getProductId()).isEqualTo("5");
        assertThat(order.getOrderLines().get(1).getNrOfItems()).isEqualTo(1);
    }
}
