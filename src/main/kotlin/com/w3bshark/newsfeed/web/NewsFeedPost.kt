//package com.w3bshark.newsfeed.web
//
//import com.w3bshark.newsfeed.data.IPost
//import com.w3bshark.newsfeed.data.User
//
///**
// * NewsFeed Post data class used specifically for the mobile API endpoint
// */
//data class NewsFeedPost(
//        override val id: Int,
//        override val createdAt: String,
//        override val createdByUserId: Int,
//        override val text: String?,
//        override val imageUrl: String?,
//        override val link: String?,
//        val user: User
//) : IPost