package br.com.jelsonjr.controllers

import br.com.jelsonjr.models.dtos.CreateUserDTO
import br.com.jelsonjr.services.UserService
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import org.bson.types.ObjectId

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserController(private val userService: UserService) {

    @Context
    lateinit var uriInfo: UriInfo

    @GET
    fun getUsers(
        @QueryParam("page") page: Int?,
        @QueryParam("size") size: Int?,
        @QueryParam("sortField") sortField: String?,
        @QueryParam("sortDirection") sortDirection: String?,
    ): Response {
        val users = userService.getAll(page, size, sortField, sortDirection)
        return Response.ok(users).build()
    }

    @GET
    @Path("/{id}")
    fun getUserById(@PathParam("id") id: String): Response {
        val objectId = ObjectId(id)
        val user = userService.getById(objectId)

        return Response.ok(user).build()
    }

    @POST
    fun registerUser(@Valid dto: CreateUserDTO): Response {
        val userRegistered = userService.create(dto)
        val uri = uriInfo.absolutePathBuilder.path(userRegistered.id.toString()).build()

        return Response.created(uri).entity(userRegistered).build()
    }
}
