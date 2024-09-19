package br.com.jelsonjr.extensions

import br.com.jelsonjr.models.User
import br.com.jelsonjr.rest.dtos.CreateUserDTO

fun CreateUserDTO.toUser() = User(
    name = this.name,
    email = this.email,
    password = User.hashPassword(this.password),
    phone = this.phone
)