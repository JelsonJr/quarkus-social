package br.com.jelsonjr.models.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern

data class CreateUserDTO(
    @field:NotBlank(message = "Name must not be blank")
    @field:Size(max = 30, message = "Name must not exceed 30 characters")
    val name: String = "",

    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email must not be blank")
    val email: String = "",

    @field:NotBlank(message = "Password must not be blank")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    val password: String = "",

    @field:Pattern(regexp = "\\d{11}", message = "Phone must be exactly 11 digits")
    val phone: String? = null
)