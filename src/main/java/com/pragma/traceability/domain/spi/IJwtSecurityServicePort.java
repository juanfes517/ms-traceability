package com.pragma.traceability.domain.spi;

public interface IJwtSecurityServicePort {

    boolean validateToken(String token);

    String getClaim(String token, String claim);

    String getSubject(String token);

    String getSubject();
}
