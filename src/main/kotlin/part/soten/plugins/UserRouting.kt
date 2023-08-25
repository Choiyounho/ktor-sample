package part.soten.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import part.soten.Sample
import part.soten.User
import part.soten.user.UserRequest
import part.soten.user.UserResponse

fun Application.userRouting() {
    routing {
        get("/") {
            call.respondText("Hello world")
        }

        get("/user/{id}") {
            call.respond(Sample())
        }
        get("/users") {
            val users = transaction {
                User.all().map {
                    UserResponse(it.id.value, it.name, it.email)
                }
            }

            call.respond(users)
        }

        post("/user") {
            val userRequest = call.receive<UserRequest>()
            transaction {
                User.new {
                    name = userRequest.name
                    email = userRequest.email
                }
            }

            call.respond(userRequest)
        }
    }
}
