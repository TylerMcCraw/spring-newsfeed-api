package com.w3bshark.newsfeed

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.select
import org.springframework.transaction.annotation.Transactional

/**
 * Main Application class
 *
 * Initialization will currently drop all tables and rebuild them with test data when run - for testing purposes
 */
@SpringBootApplication
open class Application {
    @Bean
    open fun transactionManager(dataSource: DataSource) = SpringTransactionManager(dataSource)

    //    @Bean
//    open fun init(userRepo: IUserRepository,
//            friendRepo: IFriendRelationshipRepository,
//            postRepo: IPostRepository) = CommandLineRunner {
    @Bean
    open fun init() = CommandLineRunner {
        transaction {
            drop(Users)
            drop(Posts)
            drop(FriendRelationships)

            create(Users)
            create(Posts)
            create(FriendRelationships)

            val walter = User.new {
                emailAddr = "walterwhite@lospolloshermanos.com"
                firstName = "Walter"
                lastName = "White"
                photoUrl = "https://vignette.wikia.nocookie.net/smashbroslawlorigins/images/b/b8/WalterWhite.jpg"
            }
            val jesse = User.new {
                emailAddr = "jessepinkman@lospolloshermanos.com"
                firstName = "Jesse"
                lastName = "Pinkman"
                photoUrl = "https://cdn.costumewall.com/wp-content/uploads/2017/03/jesse-pinkman.jpg"
            }
            val mike = User.new {
                emailAddr = "mikeehrmantraut@lospolloshermanos.com"
                firstName = "Mike"
                lastName = "Ehrmantraut"
                photoUrl = "https://vignette1.wikia.nocookie.net/breakingbad/images/8/8d/BCS_S3_MikeEhrmantraut.jpg"
            }

            FriendRelationship.new {
                requester = walter
                acceptor = jesse
            }
            FriendRelationship.new {
                requester = jesse
                acceptor = walter
            }
            FriendRelationship.new {
                requester = walter
                acceptor = mike
            }
            FriendRelationship.new {
                requester = mike
                acceptor = walter
            }
            FriendRelationship.new {
                requester = mike
                acceptor = jesse
            }
            FriendRelationship.new {
                requester = jesse
                acceptor = mike
            }

            var dt = DateTime.now(DateTimeZone.UTC).minusHours(5)
            for (i in 0 until 200 step 5) {
                Post.new {
                    createdAt = dt
                    creator = jesse
                    link = "$i. http://www.google.com"
                    imageUrl = "http://content.internetvideoarchive.com/content/photos/7224/378286_011.jpg"
                }
                dt = dt.plusMinutes(1)
                Post.new {
                    createdAt = dt
                    creator = walter
                    text = "${i.plus(
                            1)}. It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness"
                }
                dt = dt.plusMinutes(1)
                Post.new {
                    createdAt = dt
                    creator = mike
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/54/Giancarlo_Esposito_%26_Bob_Odenkirk_SXSW_2017.jpg/300px-Giancarlo_Esposito_%26_Bob_Odenkirk_SXSW_2017.jpg"
                }
                dt = dt.plusMinutes(1)
                Post.new {
                    createdAt = dt
                    creator = jesse
                    text = "${i.plus(
                            3)}. It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness"
                }
                dt = dt.plusMinutes(1)
                Post.new {
                    createdAt = dt
                    creator = walter
                    text = "${i.plus(4)}. Selling my RV. Call me if you're interested. Cash Only."
                    imageUrl = "https://www.walldevil.com/wallpapers/w11/thumb/89635-breaking-bad.jpg"
                }
                dt = dt.plusMinutes(1)
            }


            (FriendRelationships innerJoin Posts).select {
                FriendRelationships.acceptor eq walter.id
            }.map { r ->
                r.data
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}