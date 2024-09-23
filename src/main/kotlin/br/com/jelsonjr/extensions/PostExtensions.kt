package br.com.jelsonjr.extensions

import br.com.jelsonjr.models.Post
import br.com.jelsonjr.models.User
import br.com.jelsonjr.rest.dtos.PostForm

fun PostForm.toPost(user: User) = Post(
    user = user,
    postType = this.postType,
    text = this.text,
    location = this.location,
    tags = this.tags ?: emptyList(),
)