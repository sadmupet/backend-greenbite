package com.greenbite.bff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {

    private Subscription subscription;
    private List<Product> products;
    private int greenPoints;
    private String userName;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String id;
        private String nombre;
        private String categoria;
        private double precio;
        private String ciudad;
    }
}
