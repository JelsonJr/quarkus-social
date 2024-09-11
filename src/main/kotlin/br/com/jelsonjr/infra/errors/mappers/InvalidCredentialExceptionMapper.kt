package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.infra.errors.exceptions.InvalidCredentialException
import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class InvalidCredentialExceptionMapper : ExceptionMapper<InvalidCredentialException> {

    override fun toResponse(exception: InvalidCredentialException): Response {
        val response = getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(response)
            .build()
    }
}