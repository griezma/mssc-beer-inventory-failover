package griezma.mssc.inventoryfailover;

import griezma.mssc.brewery.model.BeerInventoryDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsscBeerInventoryFailoverApplicationTests {

    @Autowired
    WebTestClient client;

    @Test
    void contextLoads() {}

    @Test @Disabled("passes when invoked from IDE but not from CLI??")
    void testInventoryFailoverResponse() {

        client.get().uri("/api/v1/inventory-failover").exchange()
                .expectStatus().isOk()
                .expectBodyList(BeerInventoryDto.class).hasSize(1)
                    .value(list -> list.get(0).getQuantityOnHand(), greaterThan(100));
    }
}
