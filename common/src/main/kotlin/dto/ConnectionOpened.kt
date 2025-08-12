package dto

import kotlinx.serialization.Serializable

@Serializable
class ConnectionOpened(val message: String) : ProtocolMessage