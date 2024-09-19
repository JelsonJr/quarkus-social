package br.com.jelsonjr.services

import org.bson.types.ObjectId

interface Service<Model, CreateDTO> {

    fun getById(id: ObjectId): Model
    fun create(dto: CreateDTO): Model
    fun delete(id: ObjectId): Boolean
}