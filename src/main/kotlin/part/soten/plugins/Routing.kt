package part.soten.plugins

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import part.soten.Sample
import part.soten.User
import java.io.File


fun Application.configureRouting() {
    routing {
        post("/upload") {
            val multipart = call.receiveMultipart()
            var response = "No files received"

            val uploadDir = File("uploads")
            if (!uploadDir.exists()) {
                uploadDir.mkdir()
            }

            var isObj = false

            var test: Sample? = null
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        part.contentType ?: return@forEachPart
                        val name = part.originalFileName ?: return@forEachPart
                        val file = File(uploadDir, name)

                        part.streamProvider().use { input ->
                            file.outputStream().buffered().use {
                                input.copyTo(it)
                            }
                        }
                        response = "File uploaded: $name"
                    }
                    is PartData.FormItem -> {
                        response = part.name.orEmpty()

                        if (part.name == "texttest") {
                            isObj = true

                            test = Json.decodeFromString<Sample>(part.value)
                        }
                    }
                    else -> Unit
                }
                part.dispose()
            }

            call.respond(test ?: "")
        }
    }

}