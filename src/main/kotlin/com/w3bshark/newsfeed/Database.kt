package com.w3bshark.newsfeed

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

/**
 * Database Tables
 */
object Users: IntIdTable() {
    val emailAddr = varchar("email_address", 254)
    val firstName = varchar("first_name", 300).nullable()
    val lastName = varchar("last_name", 300).nullable()
    val photoUrl = varchar("photo_url", 2000).nullable()
}

object Posts : IntIdTable() {
    val createdAt = datetime("created_at")
    val creator = reference("creator_id", Users, onDelete = CASCADE) // Will delete posts when user is deleted
    val text = varchar("text", 500).nullable()
    val imageUrl = varchar("image_url", 2000).nullable()
    val link = varchar("link", 2000).nullable()
}

object FriendRelationships: IntIdTable() {
    val requester = reference("requester_id", Users, onDelete = CASCADE) // Will delete relationship when user is deleted
    val acceptor = reference("acceptor", Users, onDelete = CASCADE) // Will delete relationship when user is deleted
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var emailAddr by Users.emailAddr
    var firstName by Users.firstName
    var lastName by Users.lastName
    var photoUrl by Users.photoUrl
    val posts by Post referrersOn Posts.creator
}

class Post(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Post>(Posts)

    var createdAt by Posts.createdAt
    var creator by User referencedOn Posts.creator
    var text by Posts.text
    var imageUrl by Posts.imageUrl
    var link by Posts.link
}

class FriendRelationship(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FriendRelationship>(FriendRelationships)

    var requester by User referencedOn FriendRelationships.requester
    var acceptor by User referencedOn FriendRelationships.acceptor
}