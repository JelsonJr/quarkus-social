package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.NotAuthorizedException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class NotAuthorizedExceptionMapper : ExceptionMapper<NotAuthorizedException> {

    override fun toResponse(exception: NotAuthorizedException): Response {
        val message = exception.message ?: "Unauthorized access"
        val response = getResponseErrorMap(exception.javaClass.simpleName, message)

        return Response
            .status(Response.Status.UNAUTHORIZED)
            .entity(response)
            .build()
    }
}