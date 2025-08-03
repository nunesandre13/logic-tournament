package webApi.http

import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR

fun runCatchingResponse(responseType: Status, action: () -> String): Response =
    try {
        val result = action()
        Response(responseType)
            .header("Content-Type", "application/json")
            .body(result)
    } catch (e: Exception) {
        Response(INTERNAL_SERVER_ERROR)
            .header("Content-Type", "application/json")
            .body(Json.encodeToString(e.message))
    }