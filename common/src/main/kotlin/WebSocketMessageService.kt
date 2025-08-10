import dto.Command
import dto.Data
import dto.Event
import dto.ProtocolMessage
import dto.WebSocketMessage

interface WebSocketMessageService <C : Command, D : Data, E : Event> {
    fun onCommand(command: C)
    fun onEvent(event: E)
    fun onData(data: D)
    fun onProtocol(message: ProtocolMessage)
    fun onOther(message: WebSocketMessage)
}