
import serializers.CommonSerializers
import serializers.PlayerSerializer
import serializers.UserSerializers
import serializers.WebSocketResponseSerializer

object Serializers {
    val commonSerializer = CommonSerializers
    val userSerializer = UserSerializers
    val playerSerializer = PlayerSerializer
    val webSocketResponseSerializer = WebSocketResponseSerializer
}