//package com.w3bshark.newsfeed.data.source
//
//import com.w3bshark.newsfeed.FriendRelationships
//import com.w3bshark.newsfeed.data.FriendRelationship
//import org.jetbrains.exposed.sql.*
//import org.jetbrains.exposed.sql.statements.UpdateBuilder
//import org.springframework.stereotype.Repository
//import org.springframework.transaction.annotation.Transactional
//
//interface IFriendRelationshipRepository : CrudRepository<FriendRelationship> {
//    /**
//     * Get list of Friends' User IDs
//     */
//    fun findFriendsForUser(userId: Int): List<Int>
//}
//
///**
// * Friend Relationship data repository
// */
//@Repository
//@Transactional
//open class FriendRelationshipRepository : IFriendRelationshipRepository {
//
//    override fun createTable() = SchemaUtils.create(FriendRelationships)
//
//    override fun create(relationship: FriendRelationship): FriendRelationship {
//        relationship.id = FriendRelationships.insert(toRow(relationship))[FriendRelationships.id]
//        return relationship
//    }
//
//    override fun findAll() = FriendRelationships.selectAll().map { fromRow(it) }
//
//    override fun find(id: Int): FriendRelationship? =
//            FriendRelationships.select { FriendRelationships.id eq id }.map { fromRow(it) }.firstOrNull()
//
//    override fun findFriendsForUser(userId: Int): List<Int> =
//            FriendRelationships.select { FriendRelationships.acceptorUserId eq userId }
//                    .map { resultRow -> resultRow[FriendRelationships.requesterUserId] }
//
//    override fun deleteAll() = FriendRelationships.deleteAll()
//
//    override fun dropTable() {
//        SchemaUtils.drop(FriendRelationships)
//    }
//
//    private fun toRow(relationship: FriendRelationship): FriendRelationships.(UpdateBuilder<*>) -> Unit = {
//        if (relationship.id != null) it[id] = relationship.id
//        it[requesterUserId] = relationship.requesterUserId
//        it[acceptorUserId] = relationship.acceptorUserId
//    }
//
//    private fun fromRow(r: ResultRow) = FriendRelationship(r[FriendRelationships.id], r[FriendRelationships.requesterUserId],
//            r[FriendRelationships.acceptorUserId])
//}