package dto

import kotlinx.serialization.Serializable

@Serializable
class ConnectionClosed(val reason: String) : ProtocolMessage