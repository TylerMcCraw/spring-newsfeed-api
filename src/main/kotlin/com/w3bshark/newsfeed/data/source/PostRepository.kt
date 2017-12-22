//package com.w3bshark.newsfeed.data.source
//
//import com.w3bshark.newsfeed.Posts
//import com.w3bshark.newsfeed.data.Post
//import org.jetbrains.exposed.sql.*
//import org.jetbrains.exposed.sql.statements.UpdateBuilder
//import org.joda.time.DateTime
//import org.joda.time.format.ISODateTimeFormat
//import org.springframework.stereotype.Repository
//import org.springframework.transaction.annotation.Transactional
//
//interface IPostRepository : CrudRepository<Post> {
//    fun findPostsForUserIds(userIds: List<Int>, limit: Int, startAt: DateTime? = null, endAt: DateTime? = null): Iterable<Post>
//}
//
///**
// * Post data repository
// */
//@Repository
//@Transactional
//open class PostRepository : IPostRepository {
//
//    override fun createTable() = SchemaUtils.create(Posts)
//
//    override fun create(post: Post): Post {
//        post.id = Posts.insert(toRow(post))[Posts.id]
//        return post
//    }
//
//    override fun findAll() = Posts.selectAll().map { fromRow(it) }
//
//    override fun find(id: Int): Post? =
//            Posts.select { Posts.id eq id }.map { fromRow(it) }.firstOrNull()
//
//    /**
//     * Find posts for a list of user IDs
//     *
//     * @param userIds - list of user IDs who are the creators of posts
//     * @param limit - maximum # of items to return
//     * @param startAt - datetime to use as a starting point in a chronological search
//     * @param endAt - datetime to use as an ending point in a chronological search
//     */
//    override fun findPostsForUserIds(userIds: List<Int>, limit: Int, startAt: DateTime?, endAt: DateTime?): Iterable<Post> {
//        return when {
//            startAt != null && endAt != null -> {
//                Posts.select { Posts.createdByUserId inList userIds and (Posts.createdAt greater startAt) and (Posts.createdAt less endAt) }
//                        .limit(limit)
//                        .sortedByDescending { Posts.createdAt }
//                        .map { fromRow(it) }
//            }
//            startAt != null && endAt == null -> {
//                Posts.select { (Posts.createdByUserId inList userIds) and (Posts.createdAt greater startAt) }
//                        .limit(limit)
//                        .sortedByDescending { Posts.createdAt }
//                        .map { fromRow(it) }
//            }
//            startAt == null && endAt != null -> {
//                Posts.select { (Posts.createdByUserId inList userIds) and (Posts.createdAt less endAt) }
//                        .limit(limit)
//                        .sortedBy { Posts.createdAt }
//                        .map { fromRow(it) }
//            }
//            else -> {
//                Posts.select { Posts.createdByUserId inList userIds }
//                        .limit(limit)
//                        .sortedByDescending { Posts.createdAt }
//                        .map { fromRow(it) }
//            }
//        }
//    }
//
//    override fun deleteAll() = Posts.deleteAll()
//
//    override fun dropTable() {
//        SchemaUtils.drop(Posts)
//    }
//
//    private fun toRow(post: Post): Posts.(UpdateBuilder<*>) -> Unit = {
//        if (post.id != null) it[id] = post.id
//        it[createdAt] = DateTime(post.createdAt)
//        it[createdByUserId] = post.createdByUserId
//        it[text] = post.text
//        if (post.imageUrl != null) it[imageUrl] = post.imageUrl
//        if (post.link != null) it[link] = post.link
//    }
//
//    private fun fromRow(r: ResultRow) =
//            Post(r[Posts.id], ISODateTimeFormat.dateTime().print(r[Posts.createdAt]), r[Posts.createdByUserId], r[Posts.text],
//                    r[Posts.imageUrl], r[Posts.link])
//}