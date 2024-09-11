package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import io.smallrye.jwt.build.JwtSignatureException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class JwtSignatureExceptionMapper : ExceptionMapper<JwtSignatureException> {

    override fun toResponse(exception: JwtSignatureException): Response {
        val response = getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .serverError()
            .entity(response)
            .build()
    }
}