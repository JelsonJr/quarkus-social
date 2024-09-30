package br.com.jelsonjr.rest.resources

import br.com.jelsonjr.rest.dtos.PostForm
import br.com.jelsonjr.services.PostService
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import org.bson.types.ObjectId

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PostController(
    @Context val uriInfo: UriInfo,
    private val service: PostService,
) {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("USER")
    fun newPost(form: PostForm): Response {
        val post = service.create(form)
        val uri = uriInfo.absolutePathBuilder.path(post.id.toString()).build()

        return Response.created(uri).entity(post).build()
    }

    @GET
    @Path("/{idUser}")
    @RolesAllowed("USER")
    fun getPost(
        @PathParam("idUser") idUser: String,
        @QueryParam("sortField") sortField: String? = "id",
        @QueryParam("sortDirection") sortDirection: String? = "asc",
        @QueryParam("page") page: Int? = 0
    ): Response {
        val posts = service.getList(ObjectId(idUser), page, sortField, sortDirection)

        return Response.ok().entity(posts).build()
    }

    @PATCH
    @Path("/like/{id}")
    @RolesAllowed("USER")
    fun likePost(@PathParam("id") id: String): Response {
        service.likePost(id)
        return Response.ok().build()
    }

    @PATCH
    @Path("/dislike/{id}")
    @RolesAllowed("USER")
    fun dislikePost(@PathParam("id") id: String): Response {
        service.dislikePost(id)
        return Response.ok().build()
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("USER")
    fun deletePost(@PathParam("id") id: String): Response {
        service.delete(ObjectId(id))
        return Response.noContent().build()
    }
}
