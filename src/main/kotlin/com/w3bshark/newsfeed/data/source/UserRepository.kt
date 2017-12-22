//package com.w3bshark.newsfeed.data.source
//
//import com.w3bshark.newsfeed.User
//import com.w3bshark.newsfeed.Users
//import com.w3bshark.newsfeed.data.User
//import org.jetbrains.exposed.sql.*
//import org.jetbrains.exposed.sql.statements.UpdateBuilder
//import org.springframework.stereotype.Repository
//import org.springframework.transaction.annotation.Transactional
//
//interface IUserRepository : CrudRepository<User> {
//    fun findUsersForIds(ids: List<Int>): Map<Int, User>
//}
//
///**
// * User data repository
// */
//@Repository
//@Transactional
//open class UserRepository : IUserRepository {
//
//    override fun createTable() = SchemaUtils.create(Users)
//
//    override fun create(user: User): User {
//        user.id = Users.insert(toRow(user))[Users.id]
//
//        User.new {
//
//        }
//        return user
//    }
//
//    override fun findAll() = Users.selectAll().map { fromRow(it) }
//
//    override fun find(id: Int): User? =
//            Users.select { Users.id eq id }.map { fromRow(it) }.firstOrNull()
//
//    override fun findUsersForIds(ids: List<Int>): Map<Int, User> =
//            Users.select { Users.id inList ids }
//                    .associateBy({it[Users.id]}, {fromRow(it)})
//
//    override fun deleteAll() = Users.deleteAll()
//
//    override fun dropTable() {
//        SchemaUtils.drop(Users)
//    }
//
//    private fun toRow(user: User): Users.(UpdateBuilder<*>) -> Unit = {
//        if (user.id != null) it[id] = user.id
//        it[emailAddr] = user.emailAddr
//        if (user.firstName != null) it[firstName] = user.firstName
//        if (user.lastName != null) it[lastName] = user.lastName
//        if (user.photoUrl != null) it[photoUrl] = user.photoUrl
//    }
//
//    private fun fromRow(r: ResultRow) = User(r[Users.id], r[Users.emailAddr], r[Users.firstName], r[Users.lastName],
//            r[Users.photoUrl])
//}