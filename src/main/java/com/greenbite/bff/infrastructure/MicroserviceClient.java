package com.greenbite.bff.infrastructure;

import com.greenbite.bff.domain.DashboardData;
import com.greenbite.bff.domain.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MicroserviceClient {

    private final WebClient suscripcionesClient;
    private final WebClient catalogoClient;

    public MicroserviceClient(
            @Value("${greenbite.ms.suscripciones.url}") String suscripcionesUrl,
            @Value("${greenbite.ms.catalogo.url}") String catalogoUrl,
            WebClient.Builder builder) {
        this.suscripcionesClient = builder.baseUrl(suscripcionesUrl).build();
        this.catalogoClient      = builder.baseUrl(catalogoUrl).build();
    }

    public Mono<Subscription> getSubscriptionByUser(Long userId) {
        return suscripcionesClient.get()
                .uri("/api/suscripciones/usuario/{id}", userId)
                .retrieve()
                .bodyToMono(Subscription.class)
                .onErrorResume(e -> Mono.empty());
    }

    @SuppressWarnings("unchecked")
    public Mono<List<DashboardData.Product>> getProductsByCity(String ciudad) {
        return catalogoClient.get()
                .uri("/api/productos?ciudad={ciudad}", ciudad)
                .retrieve()
                .bodyToFlux(DashboardData.Product.class)
                .collectList()
                .onErrorResume(e -> Mono.just(List.of()));
    }

    public Mono<Subscription> pauseSubscription(Long id) {
        return suscripcionesClient.patch()
                .uri("/api/suscripciones/{id}/pausar", id)
                .retrieve()
                .bodyToMono(Subscription.class);
    }

    public Mono<Void> cancelSubscription(Long id) {
        return suscripcionesClient.delete()
                .uri("/api/suscripciones/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
