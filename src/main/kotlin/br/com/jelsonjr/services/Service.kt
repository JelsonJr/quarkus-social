package br.com.jelsonjr.services

import br.com.jelsonjr.repositorys.Repository
import br.com.jelsonjr.types.PaginatedResponse
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import org.bson.types.ObjectId

abstract class Service<Model : Any, CreateDTO>(
    protected val repository: Repository<Model>,
) {

    fun getAll(
        page: Int? = null,
        size: Int? = null,
        sortField: String? = null,
        sortDirection: String? = null
    ): PaginatedResponse<Model> {
        val defaultPage = page ?: 0
        val defaultSize = size ?: 100
        val defaultSortField = sortField?.takeIf { it.isNotBlank() } ?: "id"
        val defaultSortDirection = sortDirection?.lowercase() ?: "asc"

        val pageable = Page.of(defaultPage, defaultSize)

        val sort = when (defaultSortDirection) {
            "desc" -> Sort.by(defaultSortField).descending()
            else -> Sort.by(defaultSortField).ascending()
        }

        val pageResult = repository
            .findAll(sort)
            .page(pageable)

        return PaginatedResponse(
            totalElements = pageResult.count(),
            totalPages = pageResult.pageCount(),
            elements = pageResult.list()
        )
    }

    fun getById(id: ObjectId): Model {
        return repository.findByIdOrThrow(id)
    }

    abstract fun create(dto: CreateDTO): Model
}
