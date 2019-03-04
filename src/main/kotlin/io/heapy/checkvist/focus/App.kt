@file:JvmName("App")

package io.heapy.checkvist.focus

import io.heapy.checkvist.api.BasicAuthenticationCredentials
import io.heapy.checkvist.api.DefaultCheckvist
import io.heapy.checkvist.api.DefaultConfiguration
import io.heapy.checkvist.jvm.configureCheckvist
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING

/**
 * Entrypoint.
 *
 * @author Ruslan Ibragimov
 * @since 1.0.0
 */
suspend fun main() {
    val checkvistId = System.getenv("CHECKVIST_ID").toLong()
    val outputFile = System.getenv("CHECKVIST_OUTPUT")

    val credentials = BasicAuthenticationCredentials(
        System.getenv("CHECKVIST_USERNAME"),
        System.getenv("CHECKVIST_PASSWORD")
    )

    val configuration = DefaultConfiguration(
        baseUrl = "https://checkvist.com",
        credentials = credentials
    )

    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                configureCheckvist()
            }
        }
    }

    val checkvist = DefaultCheckvist(
        configuration,
        client
    )

    val tasks = checkvist.tasks(
        checkvistId,
        false,
        "",
        credentials
    )

    val text = tasks
        .asSequence()
        .filter { it.tags.containsKey("Focus") }
        .map { it.parentId to it.content }
        .groupBy { (parent, _) -> parent }
        .map { group -> tasks.find { group.key == it.id }?.content to group.value.map { it.second } }
        .joinToString(separator = "\n\n") {
            "## ${it.first}\n" + it.second.joinToString(separator = "\n") { "* $it" }
        }

    @Suppress("BlockingMethodInNonBlockingContext")
    Files.write(Paths.get(outputFile), text.toByteArray(), CREATE, TRUNCATE_EXISTING)
}
