package domain

import java.time.Instant

data class RefreshToken(
    val token: String,
    val userEmail: String,
    val expiresAt: Instant
)