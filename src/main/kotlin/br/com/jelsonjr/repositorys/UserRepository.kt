package br.com.jelsonjr.repositorys

import br.com.jelsonjr.models.User
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : Repository<User>
