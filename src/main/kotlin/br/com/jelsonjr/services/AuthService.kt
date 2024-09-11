package br.com.jelsonjr.services

import br.com.jelsonjr.infra.errors.exceptions.InvalidCredentialException
import br.com.jelsonjr.infra.security.Credentials
import br.com.jelsonjr.models.User
import br.com.jelsonjr.repositorys.UserRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AuthService(private val userRepository: UserRepository) {

    fun getUser(credentials: Credentials): User {
        val user = userRepository.find("email", credentials.email).firstResult()

        return if (user != null && User.checkPassword(credentials.password, user.password)) {
            user
        } else {
            throw InvalidCredentialException("Incorrect email or password")
        }
    }
}