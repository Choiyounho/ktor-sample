package part.soten

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import part.soten.plugins.configureRouting
import part.soten.plugins.userRouting
import part.soten.user.Users

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    initDatabase()

    configureRouting()
    userRouting()
}

fun initDatabase() {
    Database.connect("jdbc:h2:./test", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(Users)
    }
}


