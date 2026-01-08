package org.controllibrary.configs.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.controllibrary.utils.exceptions.ModelNotFoundException;
import org.controllibrary.utils.res.api.ResponseHTTP;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleModelNotFound(ModelNotFoundException ex) {
        var res = new ResponseHTTP<Void>(
                null,
                ex.getMessage(),
                false,
                OffsetDateTime.now()
        );

        return RestResponse.status(Response.Status.NOT_FOUND, res);
    }

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleOptimisticLock(jakarta.persistence.OptimisticLockException ex) {
        var res = new ResponseHTTP<Void>(
                null,
                "This record was updated by another user.",
                false,
                OffsetDateTime.now()
        );

        return RestResponse.status(Response.Status.CONFLICT, res);
    }

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Map<String, String>>> handleConstraint(
            ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(v -> {
            String property = v.getPropertyPath().toString();
            String field = property.substring(property.lastIndexOf('.') + 1);
            errors.put(field, v.getMessage());
        });

        return RestResponse.status(Response.Status.BAD_REQUEST, new ResponseHTTP<>(
                errors,
                "Validation failed",
                false,
                OffsetDateTime.now()
        ));
    }

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleCircuitBreaker(
            org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException ex) {

        var res = new ResponseHTTP<Void>(
                null,
                "System temporarily overloaded (Circuit Breaker).",
                false,
                OffsetDateTime.now()
        );

        return RestResponse.status(Response.Status.SERVICE_UNAVAILABLE, res);
    }
}