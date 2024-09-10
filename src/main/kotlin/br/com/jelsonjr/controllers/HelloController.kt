package br.com.jelsonjr.controllers

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class HelloController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Not implemented yet"
}