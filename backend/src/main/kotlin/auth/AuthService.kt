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

    fun generateAccessToken(userEmail: String): String {
        return JWT.create()
            .withIssuer(jwtIssuer)
            .withClaim("userEmail", userEmail)
            .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES)) // 15 minutos
            .sign(algorithm)
    }

    fun generateAndSaveRefreshToken(userEmail: String): String {
        val token = UUID.randomUUID().toString()
        val expiration = Instant.now().plus(7, ChronoUnit.DAYS) // 7 dias
        authData.save(RefreshToken(token, userEmail, expiration))
        return token
    }

    fun deleteRefreshToken(token: String) {
        authData.deleteByToken(token)
    }

    // should return a boolean
    fun verifyRefreshToken(token: String): Email {
        val refreshToken = authData.findByToken(token)
            ?: throw Exception("Refresh token não encontrado")
        if (refreshToken.expiresAt.isBefore(Instant.now())) {
            throw Exception("Refresh token expirado")
        }
        return Email(refreshToken.userEmail)
    }

    fun generateTokens(email: String): Tokens = Tokens(generateAccessToken(email),generateAndSaveRefreshToken(email))

    fun verifyAccessToken(token: String): Email {
        try {
            val verifier = JWT.require(algorithm)
                .withIssuer(jwtIssuer)
                .build()
            val decodedJWT = verifier.verify(token)
            val userId = decodedJWT.getClaim("userEmail").asString()
                ?: throw Exception("Claim userId não encontrado")
            return Email(userId)
        } catch (e: Exception) {
            throw Exception("Token inválido ou expirado", e)
        }
    }
}