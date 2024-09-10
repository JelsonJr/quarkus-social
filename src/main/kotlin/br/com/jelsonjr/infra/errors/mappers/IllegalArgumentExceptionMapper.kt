package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class IllegalArgumentExceptionMapper : ExceptionMapper<IllegalArgumentException> {

    override fun toResponse(exception: IllegalArgumentException): Response {
        val errorResponse =
            getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(errorResponse)
            .build()
    }

}