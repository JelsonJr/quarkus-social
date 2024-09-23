package br.com.jelsonjr.services

import br.com.jelsonjr.extensions.toPost
import br.com.jelsonjr.models.Post
import br.com.jelsonjr.repositorys.PostRepository
import br.com.jelsonjr.repositorys.UserRepository
import br.com.jelsonjr.rest.dtos.PostForm
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class PostService(
    private val repository: PostRepository, private val userRepository: UserRepository
) : Service<Post, PostForm> {

    override fun create(dto: PostForm): Post {
        val user = userRepository.findByIdOrThrow(ObjectId(dto.idUser))
        val post = dto.toPost(user)
        post.fileUrl = dto.file?.fileName()
        repository.persist(post)

        user.posts.add(post)

        return post
    }

    fun getList(
        idUser: ObjectId,
        sortField: String?,
        sortDirection: String?,
    ): List<Post> {
        val defaultSortField = sortField ?: "id"
        val defaultSortDirection = sortDirection ?: "desc"

        val sort = when (defaultSortDirection) {
            "desc" -> Sort.by(defaultSortField).descending()
            else -> Sort.by(defaultSortField).ascending()
        }

        return repository.find("user", sort, idUser).list()
    }

    override fun getById(id: ObjectId): Post {
        return repository.findByIdOrThrow(id)
    }

    override fun delete(id: ObjectId): Boolean {
        return repository.deleteById(id)
    }
}