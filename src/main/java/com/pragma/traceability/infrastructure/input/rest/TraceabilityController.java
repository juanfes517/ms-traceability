package com.pragma.traceability.infrastructure.input.rest;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;
import com.pragma.traceability.application.handler.ITraceabilityHandler;
import com.pragma.traceability.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.TRACEABILITY_CONTROLLER)
public class TraceabilityController {

    private final ITraceabilityHandler traceabilityHandler;

    @Operation(summary = ApiConstants.CREATE_TRACEABILITY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiConstants.OBJECT_CREATED_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content)
    })
    @PostMapping
    public ResponseEntity<TraceabilityResponseDto> createTraceability(@Valid @RequestBody CreateTraceabilityRequestDto createTraceabilityRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(traceabilityHandler.createTraceability(createTraceabilityRequestDto));
    }

    @Operation(summary = ApiConstants.GET_TRACEABILITY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content)
    })
    @GetMapping(ApiConstants.GET_ORDER_TRACEABILITY_ENDPOINT)
    public ResponseEntity<List<TraceabilityResponseDto>> getOrderTraceability(@RequestParam Long orderId) {
        return ResponseEntity.ok(traceabilityHandler.getOrderTraceability(orderId));
    }

    @Operation(summary = ApiConstants.GET_RESTAURANT_EFFICIENCY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content)
    })
    @GetMapping(ApiConstants.GET_RESTAURANT_EFFICIENCY_ENDPOINT)
    public ResponseEntity<List<RestaurantEfficiencyResponseDto>> getRestaurantEfficiency(@RequestParam List<Long> orderIds) {
        return ResponseEntity.ok(traceabilityHandler.getRestaurantEfficiency(orderIds));
    }

    @Operation(summary = ApiConstants.GET_EMPLOYEE_EFFICIENCY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content)
    })
    @GetMapping(ApiConstants.GET_EMPLOYEE_EFFICIENCY_ENDPOINT)
    public ResponseEntity<List<EmployeeEfficiencyResponseDto>> getEmployeeEfficiency(@RequestParam List<Long> employeeIds) {
        return ResponseEntity.ok(traceabilityHandler.getEmployeeEfficiency(employeeIds));
    }
}
