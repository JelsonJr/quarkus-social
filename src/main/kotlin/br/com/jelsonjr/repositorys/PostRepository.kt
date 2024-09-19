package br.com.jelsonjr.repositorys

import br.com.jelsonjr.models.Post
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostRepository : Repository<Post>