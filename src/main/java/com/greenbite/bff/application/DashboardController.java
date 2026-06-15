package com.greenbite.bff.application;

import com.greenbite.bff.domain.DashboardData;
import com.greenbite.bff.infrastructure.MicroserviceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final MicroserviceClient microserviceClient;

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<DashboardData>> getDashboard(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "Santiago") String ciudad) {

        Mono<DashboardData> dashboardMono = Mono.zip(
                microserviceClient.getSubscriptionByUser(userId)
                        .defaultIfEmpty(new com.greenbite.bff.domain.Subscription()),
                microserviceClient.getProductsByCity(ciudad)
        ).map(tuple -> DashboardData.builder()
                .subscription(tuple.getT1())
                .products(tuple.getT2())
                .greenPoints(tuple.getT1().getGreenPoints())
                .userName("Usuario " + userId)
                .build()
        );

        return dashboardMono.map(ResponseEntity::ok);
    }
}
