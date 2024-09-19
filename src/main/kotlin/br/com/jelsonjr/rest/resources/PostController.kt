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
class PostController(private val service: PostService) {

    @Context
    lateinit var uriInfo: UriInfo

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
    ): Response {
        val posts = service.getList(ObjectId(idUser), sortField, sortDirection)

        return Response.ok().entity(posts).build()
    }
}
