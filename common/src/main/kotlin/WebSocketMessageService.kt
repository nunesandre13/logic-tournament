import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse

interface WebSocketMessageService <P : WsProtocol, T : WsRequest, E : WsResponse> {
   suspend fun onResponse(message: E)
   suspend fun onRequest(message: T)
   suspend fun onProtocol(message: P)
}