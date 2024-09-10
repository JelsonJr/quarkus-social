package br.com.jelsonjr.services

import br.com.jelsonjr.models.User
import br.com.jelsonjr.models.dtos.CreateUserDTO
import br.com.jelsonjr.repositorys.UserRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UserService : Service<User, CreateUserDTO> {

    constructor() : super(UserRepository())

    @Inject
    constructor(repository: UserRepository) : super(repository)

    override fun create(dto: CreateUserDTO): User {
        val user = User(dto)
        repository.persist(user)

        return user
    }

}