package br.com.jelsonjr.extensions

import br.com.jelsonjr.models.Post
import br.com.jelsonjr.models.User
import br.com.jelsonjr.rest.dtos.PostForm
import org.bson.types.ObjectId

fun PostForm.toPost() = Post(
    user = ObjectId(this.idUser),
    postType = this.postType,
    text = this.text,
    location = this.location,
    tags = this.tags ?: emptyList(),
)