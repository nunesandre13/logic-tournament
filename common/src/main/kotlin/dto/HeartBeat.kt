package dto

import kotlinx.serialization.Serializable

@Serializable
data class HeartBeat(val timestamp: Long = System.currentTimeMillis()): Event