package com.pragma.traceability.infrastructure.exceptionhandler;

import com.pragma.traceability.application.dto.response.ExceptionResponseDto;
import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OrderNotFromCustomerException.class)
    public ExceptionResponseDto handleOrderNotFromCustomerException(OrderNotFromCustomerException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
