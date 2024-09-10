package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper : ExceptionMapper<NotFoundException> {

    override fun toResponse(exception: NotFoundException): Response {
        val errorResponse =
            getResponseErrorMap(exception.javaClass.simpleName, "The requested resource could not be found.")

        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(errorResponse)
            .build()
    }
}