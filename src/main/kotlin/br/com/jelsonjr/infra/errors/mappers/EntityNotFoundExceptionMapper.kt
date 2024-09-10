package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.infra.errors.exceptions.EntityNotFoundException
import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class EntityNotFoundExceptionMapper : ExceptionMapper<EntityNotFoundException> {

    override fun toResponse(exception: EntityNotFoundException): Response {
        val errorResponse =
            getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(errorResponse)
            .build()
    }
}