package org.example.webApi

import Serializers
import org.http4k.core.*
import org.http4k.routing.*
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.OK
import services.PlayerServices

class PlayerWebApi(private val service: PlayerServices, private val serializer: Serializers) {
    val routes = routes(
        "/" bind POST to ::createUser
    )

    private fun getAllUsers(request: Request): Response {
            TODO()
    }

    private fun createUser(request: Request): Response {
        return try {
            val userReq = with(serializer.playerSerializer){ request.bodyString().toPlayerDetailsIn()}
            val playerCreated = service.createPlayer(userReq.playerName, userReq.email)
            Response(OK).body("Usuário criado: ${playerCreated.name} / ${playerCreated.email} / ${playerCreated.id}")
        } catch (e: Exception) {
            Response(Status.BAD_REQUEST).body("Dados inválidos")
        }
    }
}