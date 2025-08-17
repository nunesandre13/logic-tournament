import dto.WsCommunication
import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse

abstract class Dispatcher {
    suspend fun dispatch(message: WsCommunication) {
        when (message) {
            is WsRequest -> dispatchRequests(message)
            is WsResponse -> dispatchResponse(message)
            is WsProtocol -> dispatchProtocol(message)
        }
    }

    protected abstract suspend fun dispatchRequests(message: WsRequest)
    protected abstract suspend fun dispatchResponse(message: WsResponse)
    protected abstract suspend fun dispatchProtocol(message: WsProtocol)
}
