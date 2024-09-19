package br.com.jelsonjr.infra.errors.mappers

import br.com.jelsonjr.util.getResponseErrorMap
import jakarta.ws.rs.NotSupportedException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.resteasy.reactive.RestResponse

@Provider
class NotSupportedExceptionMapper : ExceptionMapper<NotSupportedException> {

    override fun toResponse(exception: NotSupportedException): Response {
        val responseError = getResponseErrorMap(exception.javaClass.simpleName, exception.message.toString())

        return Response
            .status(RestResponse.Status.UNSUPPORTED_MEDIA_TYPE)
            .entity(responseError)
            .build()
    }
}