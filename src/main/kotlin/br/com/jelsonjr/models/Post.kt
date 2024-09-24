package br.com.jelsonjr.models

import br.com.jelsonjr.models.enums.PostType
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.types.ObjectId
import java.time.LocalDateTime

@MongoEntity(collection = "posts")
data class Post(
    val user: ObjectId = ObjectId(),
    val postType: PostType = PostType.TEXT,
    val text: String = "",
    var likes: Int = 0,
    var shares: Int = 0,
    val comments: List<Comment> = emptyList(),
    var fileUrl: String? = null,
    val tags: List<String> = emptyList(),
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val location: String? = null
) : PanacheMongoEntity()

