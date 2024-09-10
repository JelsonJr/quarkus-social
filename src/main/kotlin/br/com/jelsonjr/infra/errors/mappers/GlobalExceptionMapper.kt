package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GlobalExceptionMapper : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        val errorResponse =
            getResponseErrorMap("Error", "An unexpected error occurred: ${exception.message}")

        println(exception.javaClass.name)

        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(errorResponse)
            .build()
    }
}