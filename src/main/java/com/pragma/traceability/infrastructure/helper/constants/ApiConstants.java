package com.pragma.traceability.infrastructure.helper.constants;

public class ApiConstants {

    private ApiConstants() {}

    public static final String TRACEABILITY_CONTROLLER = "/api/v1/traceability";
    public static final String GET_ORDER_TRACEABILITY_ENDPOINT = "/order-id";
    public static final String GET_RESTAURANT_EFFICIENCY_ENDPOINT = "/restaurant-efficiency";
    public static final String GET_EMPLOYEE_EFFICIENCY_ENDPOINT = "/employee-efficiency";

    public static final String CREATE_TRACEABILITY_DESCRIPTION = "Create a new traceability";
    public static final String GET_TRACEABILITY_DESCRIPTION = "Get all traceability by order id";
    public static final String GET_RESTAURANT_EFFICIENCY_DESCRIPTION = "Get the efficiency of all order of the restaurant";
    public static final String GET_EMPLOYEE_EFFICIENCY_DESCRIPTION = "Get the efficiency of all employees of the restaurant";

    public static final String OK_DESCRIPTION = "Request successful";
    public static final String OBJECT_CREATED_DESCRIPTION = "Object created";
    public static final String BAD_REQUEST_DESCRIPTION = "Bad request";
    public static final String FORBIDDEN_DESCRIPTION = "Permission denied";
}
