package br.com.jelsonjr.repositorys

import br.com.jelsonjr.infra.errors.exceptions.EntityNotFoundException
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import org.bson.types.ObjectId

interface Repository<T: Any> : PanacheMongoRepository<T> {

    fun findByIdOrThrow(id: ObjectId): T {
        val entity = findById(id)

        return entity ?: throw EntityNotFoundException("User with id $id not found")
    }
}