package be.eekhaut.kristof.aws.shop.sales.order.adapter.controller;

import be.eekhaut.kristof.aws.shop.sales.order.adapter.repo.OrderInMemoryStorage;
import be.eekhaut.kristof.aws.shop.sales.order.domain.Order;
import be.eekhaut.kristof.aws.shop.sales.order.domain.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import static be.eekhaut.kristof.aws.shop.sales.order.domain.OrderId.orderId;
import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
