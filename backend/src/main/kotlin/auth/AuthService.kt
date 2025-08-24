package auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import data.dataInterfaces.AuthData
import domain.Email
import domain.Id
import domain.RefreshToken
import domain.Tokens
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

class AuthService(
    private val jwtSecret: String,
    private val authData: AuthData,
    private val jwtIssuer: String ,
) {
    private val algorithm = Algorithm.HMAC256(jwtSecret)

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

    // should return a boolean
    fun verifyRefreshToken(token: String): Id {
        val refreshToken = authData.findByToken(token)
            ?: throw Exception("Refresh token não encontrado")
        if (refreshToken.expiresAt.isBefore(Instant.now())) {
            throw Exception("Refresh token expirado")
        }
        return refreshToken.userId
    }

    
    fun generateTokens(userId: Id): Tokens = Tokens(generateAccessToken(userId),generateAndSaveRefreshToken(userId))

    fun verifyAccessToken(token: String): Id {
        try {
            val verifier = JWT.require(algorithm)
                .withIssuer(jwtIssuer)
                .build()
            val decodedJWT = verifier.verify(token)
            val userId = decodedJWT.getClaim("userId").asLong()
                ?: throw Exception("Claim userId não encontrado")
            return Id(userId)
        } catch (e: Exception) {
            throw Exception("Token inválido ou expirado", e)
        }
    }
}