package domain

import java.time.Instant

data class RefreshToken(
    val token: String,
    val userId: Id,
    val expiresAt: Instant
)