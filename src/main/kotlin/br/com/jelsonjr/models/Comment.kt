package br.com.jelsonjr.models

import java.time.LocalDateTime

data class Comment(
    val user: User,
    val text: String,
    val creationDate: LocalDateTime = LocalDateTime.now()
)
