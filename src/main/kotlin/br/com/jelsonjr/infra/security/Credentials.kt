package br.com.jelsonjr.infra.security

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class Credentials(
    @field:Email(message = "Invalid email format")
    @field:NotBlank(message = "You must provide an email to log in")
    val email: String,

    @field:NotBlank(message = "The password must not be blank")
    val password: String,
)