package com.w3bshark.newsfeed

import com.w3bshark.newsfeed.data.FriendRelationship
import com.w3bshark.newsfeed.data.Post
import com.w3bshark.newsfeed.data.User
import com.w3bshark.newsfeed.data.source.IFriendRelationshipRepository
import com.w3bshark.newsfeed.data.source.IPostRepository
import com.w3bshark.newsfeed.data.source.IUserRepository
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

/**
 * Main Application class
 *
 * Initialization will currently drop all tables and rebuild them with test data when run - for testing purposes
 */
@SpringBootApplication
open class Application {
    @Bean
    open fun transactionManager(dataSource: DataSource) = SpringTransactionManager(dataSource)

    @Bean
    open fun init(userRepo: IUserRepository,
            friendRepo: IFriendRelationshipRepository,
            postRepo: IPostRepository) = CommandLineRunner {
        userRepo.dropTable()
        friendRepo.dropTable()
        postRepo.dropTable()

        userRepo.createTable()
        friendRepo.createTable()
        postRepo.createTable()

        val walterWhite = userRepo.create(User(
                emailAddr = "walterwhite@lospolloshermanos.com",
                firstName = "Walter",
                lastName = "White",
                photoUrl = "https://vignette.wikia.nocookie.net/smashbroslawlorigins/images/b/b8/WalterWhite.jpg"))
        val jessePinkman = userRepo.create(User(
                emailAddr = "jessepinkman@lospolloshermanos.com",
                firstName = "Jesse",
                lastName = "Pinkman",
                photoUrl = "https://cdn.costumewall.com/wp-content/uploads/2017/03/jesse-pinkman.jpg"))
        val mikeEhrmantraut = userRepo.create(User(
                emailAddr = "mikeehrmantraut@lospolloshermanos.com",
                firstName = "Mike",
                lastName = "Ehrmantraut",
                photoUrl = "https://vignette1.wikia.nocookie.net/breakingbad/images/8/8d/BCS_S3_MikeEhrmantraut.jpg"
        ))

        friendRepo.create(FriendRelationship(requesterUserId = walterWhite.id, acceptorUserId = jessePinkman.id))
        friendRepo.create(FriendRelationship(requesterUserId = jessePinkman.id, acceptorUserId = walterWhite.id))
        friendRepo.create(FriendRelationship(requesterUserId = walterWhite.id, acceptorUserId = mikeEhrmantraut.id))
        friendRepo.create(FriendRelationship(requesterUserId = mikeEhrmantraut.id, acceptorUserId = walterWhite.id))
        friendRepo.create(FriendRelationship(requesterUserId = jessePinkman.id, acceptorUserId = mikeEhrmantraut.id))
        friendRepo.create(FriendRelationship(requesterUserId = mikeEhrmantraut.id, acceptorUserId = jessePinkman.id))

        postRepo.deleteAll()
        var now = DateTime.now(DateTimeZone.UTC).minusHours(5)

        for (i in 0 until 200 step 5) {
            postRepo.create(Post(
                    createdAt = ISODateTimeFormat.dateTime().print(now),
                    createdByUserId = jessePinkman.id!!,
                    link = "$i. http://www.google.com",
                    imageUrl = "http://content.internetvideoarchive.com/content/photos/7224/378286_011.jpg"))
            now = now.plusMinutes(1)
            postRepo.create(Post(
                    createdAt = ISODateTimeFormat.dateTime().print(now),
                    createdByUserId = walterWhite.id!!,
                    text = "${i.plus(1)}. It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness"))
            now = now.plusMinutes(1)
            postRepo.create(Post(
                    createdAt = ISODateTimeFormat.dateTime().print(now),
                    createdByUserId = mikeEhrmantraut.id!!,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Giancarlo_Esposito_%26_Bob_Odenkirk_SXSW_2017.jpg/300px-Giancarlo_Esposito_%26_Bob_Odenkirk_SXSW_2017.jpg"))
            now = now.plusMinutes(1)
            postRepo.create(Post(
                    createdAt = ISODateTimeFormat.dateTime().print(now),
                    createdByUserId = jessePinkman.id!!,
                    text = "${i.plus(3)}. It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness"))
            now = now.plusMinutes(1)
            postRepo.create(Post(
                    createdAt = ISODateTimeFormat.dateTime().print(now),
                    createdByUserId = walterWhite.id!!,
                    text = "${i.plus(4)}. Selling my RV. Call me if you're interested. Cash Only.",
                    imageUrl = "https://www.walldevil.com/wallpapers/w11/thumb/89635-breaking-bad.jpg"))
            now = now.plusMinutes(1)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}