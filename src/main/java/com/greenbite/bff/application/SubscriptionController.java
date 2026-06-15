package com.greenbite.bff.application;

import com.greenbite.bff.domain.Subscription;
import com.greenbite.bff.infrastructure.MicroserviceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SubscriptionController {

    private final MicroserviceClient microserviceClient;

    @GetMapping("/usuario/{userId}")
    public Mono<ResponseEntity<Subscription>> getByUser(@PathVariable Long userId) {
        return microserviceClient.getSubscriptionByUser(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/pausar")
    public Mono<ResponseEntity<Subscription>> pause(@PathVariable Long id) {
        return microserviceClient.pauseSubscription(id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> cancel(@PathVariable Long id) {
        return microserviceClient.cancelSubscription(id)
                .thenReturn(ResponseEntity.<Void>noContent().build());
    }
}
