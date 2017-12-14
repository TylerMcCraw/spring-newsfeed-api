package com.w3bshark.newsfeed.data

/**
 * Friend Relationship data class
 */
data class FriendRelationship(
        var id: Int? = null,
        var requesterUserId: Int?,
        var acceptorUserId: Int?
)