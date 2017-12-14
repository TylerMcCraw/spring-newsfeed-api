package com.w3bshark.newsfeed

import org.jetbrains.exposed.sql.Table

/**
 * Database Tables
 */

object Posts : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val createdAt = datetime("created_at")
    val createdByUserId = integer("created_by")
    val text = varchar("text", 500).nullable()
    val imageUrl = varchar("image_url", 2000).nullable()
    val link = varchar("link", 2000).nullable()
}

object Users: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val emailAddr = varchar("email_address", 254)
    val firstName = varchar("first_name", 300).nullable()
    val lastName = varchar("last_name", 300).nullable()
    val photoUrl = varchar("photo_url", 2000).nullable()
}

object FriendRelationships: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val requesterUserId = integer("requester")
    val acceptorUserId = integer("acceptor")
}