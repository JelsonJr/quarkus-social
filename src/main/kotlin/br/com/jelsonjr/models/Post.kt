package br.com.jelsonjr.models

import br.com.jelsonjr.models.enums.PostType
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.types.ObjectId
import java.time.LocalDateTime

@MongoEntity(collection = "posts")
data class Post(
    var user: ObjectId? = null,
    var postType: PostType? = null,
    var text: String = "",
    var likes: Int = 0,
    var shares: Int = 0,
    var comments: List<Comment> = emptyList(),
    var fileUrl: String? = null,
    var tags: List<String> = emptyList(),
    var creationDate: LocalDateTime = LocalDateTime.now(),
    var location: String? = null
) : PanacheMongoEntity()
