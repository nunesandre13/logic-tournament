package auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import data.dataInterfaces.AuthData
import domain.Id
import domain.RefreshToken
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

class AuthService(
    private val jwtSecret: String,
    private val authData: AuthData
) {
    private val algorithm = Algorithm.HMAC256(jwtSecret)
    private val jwtIssuer = "api.gamesTournamentAndreNunes"

    fun generateAccessToken(userId: Id): String {
        return JWT.create()
            .withIssuer(jwtIssuer)
            .withClaim("userId", userId.id)
            .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES)) // 15 minutos
            .sign(algorithm)
    }

    fun generateAndSaveRefreshToken(userId: Id): String {
        val token = UUID.randomUUID().toString()
        val expiration = Instant.now().plus(7, ChronoUnit.DAYS) // 7 dias
        authData.save(RefreshToken(token, userId, expiration))
        return token
    }

    fun deleteRefreshToken(token: String) {
        authData.deleteByToken(token)
    }
}