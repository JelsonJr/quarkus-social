package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.infra.errors.exceptions.TokenJWTException
import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class TokentJWTExceptionMapper : ExceptionMapper<TokenJWTException> {

    override fun toResponse(exception: TokenJWTException): Response {
        val responseError = getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .serverError()
            .entity(responseError)
            .build()
    }
}