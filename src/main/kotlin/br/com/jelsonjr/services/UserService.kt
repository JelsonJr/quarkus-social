package br.com.jelsonjr.services

import br.com.jelsonjr.extensions.toUser
import br.com.jelsonjr.models.User
import br.com.jelsonjr.rest.dtos.CreateUserDTO
import br.com.jelsonjr.repositorys.UserRepository
import br.com.jelsonjr.types.PaginatedResponse
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.bson.conversions.Bson
import org.bson.types.ObjectId

@ApplicationScoped
class UserService(private val repository: UserRepository) : Service<User, CreateUserDTO> {
    override fun create(dto: CreateUserDTO): User {
        val user = dto.toUser()
        repository.persist(user)

        return user
    }

    fun getList(
        page: Int?,
        size: Int?,
        sortField: String?,
        sortDirection: String?,
        filterField: String? = null,
        filterValue: String? = null
    ): PaginatedResponse<User> {
        val defaultPage = page ?: 0
        val defaultSize = size ?: 100
        val defaultSortField = sortField ?: "id"
        val defaultSortDirection = sortDirection ?: "desc"

        val pageable = Page.of(defaultPage, defaultSize)

        val sort = when (defaultSortDirection) {
            "desc" -> Sort.by(defaultSortField).descending()
            else -> Sort.by(defaultSortField).ascending()
        }

        val pageResult = if (filterField != null && filterValue != null) {
            repository.find(
                "{'$filterField': {'\$regex': ?1, '\$options': 'i'}}",
                filterValue
            ).page(pageable)
        } else {
            repository
                .findAll(sort)
                .page(pageable)
        }

        return PaginatedResponse(
            totalElements = pageResult.count(),
            totalPages = pageResult.pageCount(),
            page = pageResult.page().index,
            elements = pageResult.list()
        )
    }

    override fun getById(id: ObjectId): User {
        return repository.findByIdOrThrow(id)
    }

    override fun delete(id: ObjectId): Boolean {
        return repository.deleteById(id)
    }

    fun getFollowers(
        objectId: ObjectId,
        page: Int = 0,
        size: Int = 10,
        sortField: String = "id",
        sortDirection: String = "asc",
        filterField: String? = null,
        filterValue: String? = null
    ): PaginatedResponse<User> {
        val user = repository.findByIdOrThrow(objectId)
        val followersIds = user.followers
        val filters = mutableListOf<Bson>(Filters.`in`("_id", followersIds))

        if (filterField != null && filterValue != null) {
            filters.add(Filters.regex(filterField, filterValue, "i"))
        }

        val sort = if (sortDirection == "asc") {
            Sorts.ascending(sortField)
        } else {
            Sorts.descending(sortField)
        }

        val query = repository.mongoCollection().find(Filters.and(filters))
            .sort(sort)
            .skip(page * size)
            .limit(size)

        val totalElements = repository.mongoCollection().countDocuments(Filters.and(filters))
        val users = query.toList()
        val totalPages = (totalElements / size).toInt() + if (totalElements % size > 0) 1 else 0

        return PaginatedResponse(
            totalElements = totalElements,
            totalPages = totalPages,
            page = page,
            elements = users
        )
    }

    fun follow(idUser: String, idToFollowing: String) {
        val user = repository.findByIdOrThrow(ObjectId(idUser))
        val userToFollowing = repository.findByIdOrThrow(ObjectId(idToFollowing))

        user.follow(userToFollowing)

        repository.update(user)
        repository.update(userToFollowing)
    }

    fun unfollow(idUser: String, idToUnfollowing: String) {
        val user = repository.findByIdOrThrow(ObjectId(idUser))
        val userToUnfollowing = repository.findByIdOrThrow(ObjectId(idToUnfollowing))

        user.unfollow(userToUnfollowing)

        repository.update(user)
        repository.update(userToUnfollowing)
    }

    fun getFollowing(
        objectId: ObjectId,
        page: Int = 0,
        size: Int = 10,
        sortField: String = "id",
        sortDirection: String = "asc",
        filterField: String? = null,
        filterValue: String? = null
    ): Any? {
        val user = repository.findByIdOrThrow(objectId)
        val followersIds = user.following
        val filters = mutableListOf<Bson>(Filters.`in`("_id", followersIds))

        if (filterField != null && filterValue != null) {
            filters.add(Filters.regex(filterField, filterValue, "i"))
        }

        val sort = if (sortDirection == "asc") {
            Sorts.ascending(sortField)
        } else {
            Sorts.descending(sortField)
        }

        val query = repository.mongoCollection().find(Filters.and(filters))
            .sort(sort)
            .skip(page * size)
            .limit(size)

        val totalElements = repository.mongoCollection().countDocuments(Filters.and(filters))
        val users = query.toList()
        val totalPages = (totalElements / size).toInt() + if (totalElements % size > 0) 1 else 0

        return PaginatedResponse(
            totalElements = totalElements,
            totalPages = totalPages,
            page = page,
            elements = users
        )
    }
}