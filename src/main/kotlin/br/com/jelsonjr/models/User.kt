package br.com.jelsonjr.models

import br.com.jelsonjr.models.dtos.CreateUserDTO
import io.quarkus.mongodb.panache.common.MongoEntity
import org.bson.types.ObjectId
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.mindrot.jbcrypt.BCrypt

@MongoEntity(collection = "users")
data class User(
    var nome: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String? = "",
    var followers: MutableList<ObjectId> = mutableListOf(),
    var following: MutableList<ObjectId> = mutableListOf(),
) : PanacheMongoEntity() {

    constructor(dto: CreateUserDTO) : this(
        dto.name,
        dto.email,
        hashPassword(dto.password),
        dto.phone,
        mutableListOf(),
        mutableListOf()
    )

    private fun addFollower(userId: ObjectId) {
        if (userId !in followers) {
            followers.add(userId)
        }
    }

    private fun removeFollower(followerId: ObjectId) {
        if (followerId in followers) {
            followers.remove(followerId)
        }
    }

    fun follow(userToFollow: User) {
        if (userToFollow.id !in following) {
            following.add(userToFollow.id!!)
            userToFollow.addFollower(this.id!!)
        }
    }

    fun unfollow(userToUnfollow: User) {
        if (userToUnfollow.id in following) {
            following.remove(userToUnfollow.id!!)
            userToUnfollow.removeFollower(this.id!!)
        }
    }

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
