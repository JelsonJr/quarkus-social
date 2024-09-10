package br.com.jelsonjr.types

data class PaginatedResponse<T>(
    val totalElements: Long,
    val totalPages: Int,
    val elements: List<T>
)