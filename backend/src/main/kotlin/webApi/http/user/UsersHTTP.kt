package webApi.http.user

import Serializers
import auth.AuthService
import domain.Email
import domain.Id
import dto.UserCreationDTO
import org.http4k.core.*
import org.http4k.routing.*
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.slf4j.LoggerFactory
import serializers.UserSerializers.toJson
import services.ServicesInterfaces.IUsersServices
import toOUT
import webApi.http.runCatchingResponse

class UsersHTTP(private val service: IUsersServices, private val serializer: Serializers, private val authService: AuthService) {

    private val logger = LoggerFactory.getLogger(UsersHTTP::class.java)

    val routes = routes(
        "/" bind POST to ::createUser,
        "/" bind Method.GET to ::getAllUsers,
        "{id}" bind Method.GET to ::getUserById,
    )

    private fun getUserById(request: Request) = runCatchingResponse(OK){
        service.getUserById(Id(request.path("id")?.toLong()?: throw IllegalStateException()))?.toOUT()?.toJson() ?: throw IllegalStateException()
    }

    private fun getAllUsers(request: Request) = runCatchingResponse(OK){
        with(serializer.userSerializer){ service.listUsers().map { it.toOUT() }.toJson()}
    }


    // deve retornar tokens de acesso
    private fun createUser(request: Request) = runCatchingResponse(CREATED) {
        logger.debug("body String ${request.bodyString()}")
        val userReq: UserCreationDTO = with(serializer.userSerializer) { request.bodyString().toUserIn() }
        with(serializer.userSerializer){service.createUser(userReq.name, Email(userReq.email),userReq.password).toOUT().toJson()}
    }

    private fun logIn(request: Request) = runCatchingResponse(OK) {
        val logData = with(serializer.userSerializer) { request.bodyString().toLogInUser() }
        if (service.authenticate(logData.email,logData.password)){
            kotlinx.serialization.json.Json.encodeToString(authService.generateTokens(logData.email)) // to be removed
        } else throw IllegalStateException("")
    }

}