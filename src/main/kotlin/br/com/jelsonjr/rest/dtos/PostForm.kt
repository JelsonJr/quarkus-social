package br.com.jelsonjr.rest.dtos

import br.com.jelsonjr.models.enums.PostType
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.multipart.FileUpload

class PostForm {
    @RestForm
    lateinit var idUser: String

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
