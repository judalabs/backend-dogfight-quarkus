package org.judalabs.exceptionhandler;


import org.hibernate.exception.ConstraintViolationException;
import org.judalabs.UnprocessableEntityException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(422).entity("constraint violation").build();
    }
}
