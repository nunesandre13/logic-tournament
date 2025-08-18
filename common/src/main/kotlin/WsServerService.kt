import dto.WsCommunication
import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse
import kotlinx.coroutines.flow.merge

class WsServerService<Request: WsRequest, Response: WsResponse, Protocol: WsProtocol> {

    private val WsChannel = WebSocketChannel<Response, Request, Protocol>()

    /**
     * responsible to receive from a socket and pass it to the app
     */
    suspend fun emitFromSocket(message: Request){
        WsChannel.sendToApp(message)
    }

    suspend fun emitFromSocket(protocol: Protocol){
        WsChannel.sendProtocolToApp(protocol)
    }

    /**
     * responsible to receive from app and pass it to the socket
     */
    suspend fun emitToSocket(message: Response){
        WsChannel.sendToSocket(message)
    }

    suspend fun emitToSocket(protocol: Protocol){
        WsChannel.sendProtocolToSocket(protocol)
    }

    /**
     * This function should be responsible to have a handler that receives a WsCommunication and sends it to the socket
     * because of merge between flows it uses the main type WsCommunication, but response is either a WsProtocol of a WsResponse
     */
    suspend fun readFromChannelToSocket(handler: (WsCommunication) -> Unit) {
        merge(WsChannel.fromApp(), WsChannel.fromAppProtocol()).collect { response ->
            handler(response)
        }
    }

    suspend fun onRequest(handler: suspend (Request) -> Unit){
        WsChannel.fromSocket().collect { request ->
            handler(request)
        }
    }

    suspend fun onProtocol(handler: suspend (Protocol) -> Unit) {
        WsChannel.fromSocketProtocol().collect { request ->
            handler(request)
        }
    }

}