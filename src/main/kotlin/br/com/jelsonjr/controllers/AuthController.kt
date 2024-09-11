package br.com.jelsonjr.controllers

import br.com.jelsonjr.infra.security.Credentials
import br.com.jelsonjr.infra.security.JwtUtils
import br.com.jelsonjr.services.AuthService
import jakarta.annotation.security.PermitAll
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.validation.Valid
import jakarta.ws.rs.core.Response

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AuthController(
    private val authService: AuthService,
    private val jwtUtils: JwtUtils,
) {

    @POST
    @PermitAll
    fun login(@Valid credentials: Credentials): Response {
        val user = authService.getUser(credentials)
        val roles: Set<String> = setOf("USER")
        val token = jwtUtils.generateToken(user.email, roles)

        return Response.ok().entity(mapOf("token" to token)).build()
    }
}