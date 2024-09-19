package br.com.jelsonjr.models

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.types.ObjectId
import org.mindrot.jbcrypt.BCrypt

@MongoEntity(collection = "users")
data class User(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String? = "",
    var followers: MutableList<ObjectId> = mutableListOf(),
    var following: MutableList<ObjectId> = mutableListOf(),
    var posts: MutableList<Post> = mutableListOf()
) : PanacheMongoEntity() {

    companion object {
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }

        fun checkPassword(password: String, hashed: String): Boolean {
            return BCrypt.checkpw(password, hashed)
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
