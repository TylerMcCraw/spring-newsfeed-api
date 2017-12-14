package com.w3bshark.newsfeed.data.source

/**
 * CRUD (create, read, update, delete) Repository contract
 */
interface CrudRepository<T> {
    fun createTable()
    fun create(obj: T): T
    fun findAll(): Iterable<T>
    fun find(id: Int): T?
    fun deleteAll(): Int
    fun dropTable()
}