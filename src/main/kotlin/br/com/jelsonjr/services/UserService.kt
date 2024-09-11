package br.com.jelsonjr.services

import br.com.jelsonjr.models.User
import br.com.jelsonjr.models.dtos.CreateUserDTO
import br.com.jelsonjr.repositorys.UserRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.bson.types.ObjectId

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
}