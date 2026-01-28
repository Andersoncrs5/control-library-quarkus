package org.control.library.configs.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.control.library.utils.exception.ModelNotFoundException;
import org.control.library.utils.exception.NotAuthenticatedException;
import org.control.library.utils.res.ResponseHTTP;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleNotAuthenticated(NotAuthenticatedException ex) {
        var res = new ResponseHTTP<Void>(
                null,
                ex.getMessage(),
                false
        );

        return RestResponse.status(Response.Status.UNAUTHORIZED, res);
    }

    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleModelNotFound(ModelNotFoundException ex) {
        var res = new ResponseHTTP<Void>(
                null,
                ex.getMessage(),
                false
        );

        return RestResponse.status(Response.Status.NOT_FOUND, res);
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
                false
        ));
    }


    @ServerExceptionMapper
    public RestResponse<ResponseHTTP<Void>> handleCircuitBreaker(
            org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException ex) {

        var res = new ResponseHTTP<Void>(
                null,
                "System temporarily overloaded (Circuit Breaker).",
                false
        );

        return RestResponse.status(Response.Status.SERVICE_UNAVAILABLE, res);
    }
}
