package com.pragma.traceability.infrastructure.helper.constants;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final String CUSTOMER_ROLE = "CUSTOMER";

    private static final String[] PUBLIC_ENDPOINTS = {
            "/v1/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

    private static final String[] CUSTOMER_ENDPOINTS = {
            "/api/v1/traceability/order-id",
    };

    public static String[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS.clone();
    }

    public static String[] getCustomerEndpoints() {
        return CUSTOMER_ENDPOINTS.clone();
    }
}
