import dto.WebSocketMessage

interface WebSocketMessageDispatcher{
    fun dispatch(message: WebSocketMessage)
}