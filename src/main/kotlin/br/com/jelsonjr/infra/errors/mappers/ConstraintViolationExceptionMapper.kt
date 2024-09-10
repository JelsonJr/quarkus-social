package br.com.jelsonjr.infra.errors.mappers

import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ConstraintViolationExceptionMapper : ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(exception: ConstraintViolationException): Response {
        val violations = exception.constraintViolations.map {
            mapOf(
                "field" to it.propertyPath.last().name,
                "message" to it.message
            )
        }

        val errorMap = mapOf(
            "error" to "Validation Error",
            "message" to "One or more fields have invalid values. Please correct the following errors:",
            "errors" to violations,
            "success" to false,
        )

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errorMap)
            .build()
    }
}