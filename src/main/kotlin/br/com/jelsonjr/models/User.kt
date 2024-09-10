package br.com.jelsonjr.models

import br.com.jelsonjr.models.dtos.CreateUserDTO
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

data class User(
    var nome: String,
    var email: String,
    var password: String,
    var phone: String? = ""
) : PanacheMongoEntity() {
    constructor(dto: CreateUserDTO) : this(
        dto.name,
        dto.email,
        hashPassword(dto.password),
        dto.phone
    )

    companion object {
        private fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }

        fun checkPassword(password: String, hashed: String): Boolean {
            return BCrypt.checkpw(password, hashed)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}