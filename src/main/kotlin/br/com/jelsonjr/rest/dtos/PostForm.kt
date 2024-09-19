package br.com.jelsonjr.rest.dtos

import br.com.jelsonjr.models.User
import br.com.jelsonjr.models.enums.PostType
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.multipart.FileUpload

class PostForm {
    @RestForm
    lateinit var user: User

    @RestForm
    lateinit var text: String

    @RestForm
    lateinit var postType: PostType

    @RestForm("file")
    var file: FileUpload? = null

    @RestForm
    var tags: List<String>? = null

    @RestForm
    var location: String? = null
}
