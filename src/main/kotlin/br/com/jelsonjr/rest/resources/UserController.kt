package br.com.jelsonjr.rest.resources

import br.com.jelsonjr.rest.dtos.CreateUserDTO
import br.com.jelsonjr.services.UserService
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
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
    @RolesAllowed("USER")
    fun getUsers(
        @QueryParam("page") page: Int?,
        @QueryParam("size") size: Int?,
        @QueryParam("sortField") sortField: String? = "id",
        @QueryParam("sortDirection") sortDirection: String? = "asc",
        @QueryParam("filterField") filterField: String? = null,
        @QueryParam("filterValue") filterValue: String? = null
    ): Response {
        val users = userService.getList(page, size, sortField, sortDirection, filterField, filterValue)
        return Response.ok(users).build()
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USER")
    fun getUserById(@PathParam("id") id: String): Response {
        val objectId = ObjectId(id)
        val user = userService.getById(objectId)

        return Response.ok(user).build()
    }

    @POST
    @PermitAll
    fun registerUser(@Valid dto: CreateUserDTO): Response {
        val userRegistered = userService.create(dto)
        val uri = uriInfo.absolutePathBuilder.path(userRegistered.id.toString()).build()

        return Response.created(uri).entity(userRegistered).build()
    }

    @PATCH
    @Path("/{idUser}/follow/{idToFollowing}")
    @RolesAllowed("USER")
    fun follow(@PathParam("idUser") idUser: String, @PathParam("idToFollowing") idToFollowing: String): Response {
        userService.follow(idUser, idToFollowing)

        return Response.noContent().build()
    }

    @PATCH
    @Path("/{idUser}/unfollow/{idToUnfollowing}")
    @RolesAllowed("USER")
    fun unfollow(@PathParam("idUser") idUser: String, @PathParam("idToUnfollowing") idToUnfollowing: String): Response {
        userService.unfollow(idUser, idToUnfollowing)

        return Response.noContent().build()
    }

    @PATCH
    @Path("/{idUser}/remove/follower/{idFollower}")
    @RolesAllowed("USER")
    fun removeFollower(@PathParam("idUser") idUser: String, @PathParam("idFollower") idFollower: String): Response {
        userService.unfollow(idFollower, idUser)

        return Response.noContent().build()
    }
}
