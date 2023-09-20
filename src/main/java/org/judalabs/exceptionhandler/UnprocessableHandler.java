package org.judalabs.exceptionhandler;


import org.judalabs.UnprocessableEntityException;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnprocessableHandler implements ExceptionMapper<UnprocessableEntityException> {

    @Override
    public Response toResponse(UnprocessableEntityException exception) {
        return Response.status(422).entity("unprocessable entity").build();
    }
}
