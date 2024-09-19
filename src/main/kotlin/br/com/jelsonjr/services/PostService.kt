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
        val post = dto.toPost()
        post.fileUrl = dto.file?.fileName()
        repository.persist(post)

        val user = userRepository.findByIdOrThrow(post.user.id!!)
        user.posts.add(post)

        return post
    }

    fun getList(
        sortField: String?,
        sortDirection: String?,
    ): List<Post> {
        val defaultSortField = sortField ?: "id"
        val defaultSortDirection = sortDirection ?: "desc"

        val sort = when (defaultSortDirection) {
            "desc" -> Sort.by(defaultSortField).descending()
            else -> Sort.by(defaultSortField).ascending()
        }

        return repository.findAll(sort).list()
    }

    override fun getById(id: ObjectId): Post {
        return repository.findByIdOrThrow(id)
    }

    override fun delete(id: ObjectId): Boolean {
        return repository.deleteById(id)
    }
}