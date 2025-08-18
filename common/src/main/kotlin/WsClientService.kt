import dto.WsCommunication
import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse
import kotlinx.coroutines.flow.merge

class WsClientService<Request : WsRequest, Response : WsResponse, Protocol : WsProtocol> {

    private val WsChannel = WebSocketChannel<Request, Response, Protocol>()

    /**
     * responsável por receber do socket e passar para a app
     */
    suspend fun emitFromSocket(message: Response) {
        WsChannel.sendToApp(message)
    }

    suspend fun emitFromSocket(protocol: Protocol) {
        WsChannel.sendProtocolToApp(protocol)
    }

    /**
     * responsável por receber da app e passar para o socket
     */
    suspend fun emitToSocket(message: Request) {
        WsChannel.sendToSocket(message)
    }

    suspend fun emitToSocket(protocol: Protocol) {
        WsChannel.sendProtocolToSocket(protocol)
    }

    /**
     * Esta função é responsável por ter um handler que recebe um WsCommunication e envia para o socket.
     * Como junta flows, usa o tipo comum WsCommunication, mas a saída pode ser WsProtocol ou WsRequest.
     */
    suspend fun readFromChannelToSocket(handler: (WsCommunication) -> Unit) {
        merge(WsChannel.fromApp(), WsChannel.fromAppProtocol()).collect { request ->
            handler(request)
        }
    }

    suspend fun onResponse(handler: suspend (Response) -> Unit) {
        WsChannel.fromSocket().collect { response ->
            handler(response)
        }
    }

    suspend fun onProtocol(handler: suspend (Protocol) -> Unit) {
        WsChannel.fromSocketProtocol().collect { protocol ->
            handler(protocol)
        }
    }
}
