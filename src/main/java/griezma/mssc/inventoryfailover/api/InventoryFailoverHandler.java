package griezma.mssc.inventoryfailover.api;

import griezma.mssc.brewery.model.BeerInventoryDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class InventoryFailoverHandler {
    static final UUID ZERO_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    static final UUID INVENTORY_ID = UUID.randomUUID();

    @Bean
    RouterFunction inventoryRoute() {
        return route(GET("/api/v1/inventory-failover").and(accept(MediaType.APPLICATION_JSON)), this::listInventory);
    }

    Mono<ServerResponse> listInventory(ServerRequest request) {
        BeerInventoryDto fallbackInventory = BeerInventoryDto.builder()
                .id(INVENTORY_ID)
                .beerId(ZERO_UUID)
                .quantityOnHand(999)
                .created(OffsetDateTime.now())
                .lastModified(OffsetDateTime.now())
                .build();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(List.of(fallbackInventory)), List.class);
    }
}
