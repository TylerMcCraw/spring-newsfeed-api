package com.w3bshark.newsfeed.data

/**
 * Post data model contract
 */
interface IPost {
    val id: Int?
    val createdAt: String // Formatted as ISO UTC DateTime String
    val createdByUserId: Int
    val text: String?
    val imageUrl: String?
    val link: String?
}

/**
 * Post data class
 */
data class Post(
        override var id: Int? = null,
        override var createdAt: String, // Formatted as ISO UTC DateTime String
        override var createdByUserId: Int,
        override var text: String? = null,
        override var imageUrl: String? = null,
        override var link: String? = null
) : IPost