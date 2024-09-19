package br.com.jelsonjr.rest.resources

import jakarta.annotation.security.PermitAll
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class HelloController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    fun hello() = "Not implemented yet"
}