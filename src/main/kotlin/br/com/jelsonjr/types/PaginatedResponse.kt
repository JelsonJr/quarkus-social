package br.com.jelsonjr.types

data class PaginatedResponse<T>(
    val totalElements: Long,
    val totalPages: Int,
    val page: Int,
    val elements: List<T>
)