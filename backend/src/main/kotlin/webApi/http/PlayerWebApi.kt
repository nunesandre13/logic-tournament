//package org.example.webApi
//
//import org.http4k.core.*
//import org.http4k.routing.*
//import org.http4k.lens.*
//import org.example.services.PlayerService
//import org.example.webApi.lenses.createUserLens
//import org.http4k.core.Method.POST
//import org.http4k.core.Status.Companion.OK
//
//
//class PlayerWebApi(private val service: PlayerService) {
//    val routes = routes("/" bind POST to ::createUser,
//        )
//
//    private fun createUser(request: Request): Response {
//        return try {
//            val userReq = createUserLens(request)
//            val playerCreated = service.createPlayer(userReq.name, userReq.email)
//            Response(OK).body("Usuário criado: ${playerCreated.name} / ${playerCreated.email} / ${playerCreated.id}")
//        } catch (e: LensFailure) {
//            Response(Status.BAD_REQUEST).body("Dados inválidos")
//        }
//    }
//}