package br.com.jelsonjr.services

import br.com.jelsonjr.extensions.toPost
import br.com.jelsonjr.models.Post
import br.com.jelsonjr.repositorys.PostRepository
import br.com.jelsonjr.repositorys.UserRepository
import br.com.jelsonjr.rest.dtos.PostForm
import br.com.jelsonjr.types.PaginatedResponse
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.inputStream

@ApplicationScoped
class PostService(
    private val repository: PostRepository, private val userRepository: UserRepository
) : Service<Post, PostForm> {

    override fun create(dto: PostForm): Post {
        val user = userRepository.findByIdOrThrow(ObjectId(dto.idUser))
        val post = dto.toPost()

        dto.file?.let {
            val fileName = it.fileName()
            post.fileUrl = saveFile(it.uploadedFile().inputStream(), fileName)
        }

        user.posts.add(post)

        repository.persist(post)
        userRepository.update(user)

        return post
    }

    fun getList(
        idUser: ObjectId,
        page: Int?,
        sortField: String?,
        sortDirection: String?,
    ): PaginatedResponse<Post> {
        val defaultSortField = sortField ?: "id"
        val defaultSortDirection = sortDirection ?: "desc"

        val sort = when (defaultSortDirection) {
            "desc" -> Sort.by(defaultSortField).descending()
            else -> Sort.by(defaultSortField).ascending()
        }

        val pageResult = repository.find("user", sort, idUser).page(Page.of(0, 30))

        return PaginatedResponse(
            totalElements = pageResult.count(),
            totalPages = pageResult.pageCount(),
            page = pageResult.page().index,
            elements = pageResult.list()
        )
    }

    override fun getById(id: ObjectId): Post {
        return repository.findByIdOrThrow(id)
    }

    override fun delete(id: ObjectId): Boolean {
        removeFile(repository.findByIdOrThrow(id))

        return repository.deleteById(id)
    }

    fun likePost(id: String) {
        val post = repository.findByIdOrThrow(ObjectId(id))
        post.likes++

        repository.update(post)
    }

    fun dislikePost(id: String) {
        val post = repository.findByIdOrThrow(ObjectId(id))
        post.likes--

        repository.update(post)
    }

    private fun removeFile(post: Post) {
        val fileUrl = post.fileUrl

        if (fileUrl != null) {
            val path = Paths.get(fileUrl)
            Files.deleteIfExists(path)
        }
    }

    private fun saveFile(file: InputStream, fileName: String): String {
        val uploadDir = Paths.get("upload")

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }

        val filePath = uploadDir.resolve(fileName)
        Files.copy(file, filePath, StandardCopyOption.REPLACE_EXISTING)

        return filePath.toString()
    }
}
