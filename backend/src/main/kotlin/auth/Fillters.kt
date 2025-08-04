package auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status


val jwtSecret = System.getenv("JWT_SECRET") ?: "sua_chave_secreta_aqui_muito_forte"
val algorithm: Algorithm? = Algorithm.HMAC256(jwtSecret)
val verifier: JWTVerifier = JWT.require(algorithm).withIssuer("api.gamesTournamentAndreNunes").build()


val authFilter = Filter { next: HttpHandler ->
    { request ->
        val token = request.header("Authorization")?.substringAfter("Bearer ")
        if (token == null) {
            Response(Status.UNAUTHORIZED).body("Token não fornecido.")
        } else {
            try {
                verifier.verify(token)
                next(request)
            } catch (e: Exception) {
                Response(Status.UNAUTHORIZED).body("Token inválido ou expirado.")
            }
        }
    }
}
