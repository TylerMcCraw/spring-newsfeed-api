//package com.w3bshark.newsfeed.web
//
//import com.w3bshark.newsfeed.FriendRelationship
//import com.w3bshark.newsfeed.FriendRelationships
//import com.w3bshark.newsfeed.FriendRelationships.acceptor
//import com.w3bshark.newsfeed.FriendRelationships.requester
//import com.w3bshark.newsfeed.Post
//import com.w3bshark.newsfeed.Posts
//import com.w3bshark.newsfeed.User
//import com.w3bshark.newsfeed.Users
//import com.w3bshark.newsfeed.data.User
//import com.w3bshark.newsfeed.data.source.IFriendRelationshipRepository
//import com.w3bshark.newsfeed.data.source.IPostRepository
//import com.w3bshark.newsfeed.data.source.IUserRepository
//import org.jetbrains.exposed.sql.and
//import org.jetbrains.exposed.sql.or
//import org.jetbrains.exposed.sql.select
//import org.joda.time.DateTime
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
///**
// * Rest Controller for Post-specific API endpoints
// */
//
//@RestController
//class PostController {
//
////fun doStuff() {
////    val user = User.findById(1)!!
////    val posts = user.posts
////    Post.find { Posts.createdAt greater DateTime.now() }
////    (Users innerJoin Posts).select {
////        Posts.createdAt greater DateTime.now()
////    }
////}
//
//    /**
//     * Our main NewsFeed API
//     *
//     * Normally, this wouldn't have User ID built within the URL path,
//     * but we're using this to quickly prototype and test our different users
//     *
//     * @param count - maximum limit of # of items to return
//     * @param sinceId - ID of post to be used as an ending point in a chronological search
//     *      (in other words - get me the posts that have been created since this particular Post ID)
//     * @param maxId - ID of post to be used as a starting point in a chronological search
//     *      (in other words - get me the next several posts that have occurred after this particular Post ID)
//     */
//    @GetMapping("/{userId}/feed")
//    fun userFeed(@PathVariable(value = "userId") userId: String,
//            @RequestParam(value = "count", defaultValue = "20", required = false) count: Int,
//            @RequestParam(value = "since_id", required = false) sinceId: Int?,
//            @RequestParam(value = "max_id", required = false) maxId: Int?): Iterable<NewsFeedPost>? {
//        val userIdInt: Int = try {
//            userId.toInt()
//        } catch (e: NumberFormatException) {
//            // TODO throw fatal error
//            0
//        }
//
//        val user = User.findById(userIdInt)
//
//        // Get the user's friends
//        (FriendRelationships innerJoin Posts).select {
//            FriendRelationships.acceptor eq userId
//        }.map { r ->
//            r.get()
//        }
//
//        FriendRelationships.select { FriendRelationships.acceptorUserId eq userId }
//                .map { resultRow -> resultRow[FriendRelationships.requesterUserId] }
//
//        val friendIds = friendsRepo.findFriendsForUser(userIdInt)
//        val userIds = mutableListOf<Int>()
//        userIds.addAll(friendIds)
//        // Include current user in posts list
//        userIds.add(user.id!!)
//
//        // Get user map - ID -> User object
//        val friendsMap = userRepo.findUsersForIds(userIds)
//
//        when {
//            sinceId != null -> {
//                // Get newer posts ending at post with ID == sinceId
//                val mostRecentSyncedPost = postRepo.find(sinceId)
//                mostRecentSyncedPost?.let {
//                    return postRepo.findPostsForUserIds(userIds, count, endAt = DateTime(it.createdAt))
//                            .map {
//                                NewsFeedPost(it.id!!, it.createdAt, it.createdByUserId, it.text, it.imageUrl, it.link,
//                                        friendsMap[it.createdByUserId] ?: User())
//                            }
//                } ?: return listOf()
//            }
//            maxId != null -> {
//                // Get older posts starting at post with ID == maxId
//                val oldestSyncedPost = postRepo.find(maxId)
//                oldestSyncedPost?.let {
//                    return postRepo.findPostsForUserIds(userIds, count, startAt = DateTime(it.createdAt))
//                            .map {
//                                NewsFeedPost(it.id!!, it.createdAt, it.createdByUserId, it.text, it.imageUrl, it.link,
//                                        friendsMap[it.createdByUserId] ?: User())
//                            }
//                } ?: return listOf()
//            }
//            else ->
//                // Get all posts from friends
//                return postRepo.findPostsForUserIds(userIds, count)
//                        .map {
//                            NewsFeedPost(it.id!!, it.createdAt, it.createdByUserId, it.text, it.imageUrl, it.link,
//                                    friendsMap[it.createdByUserId] ?: User())
//                        }
//        }
//    }
//}