
import domain.Id
import domain.Player
import dto.IdDTO
import dto.PlayerDTO

fun Player.toDTO(): PlayerDTO = PlayerDTO(id = this.id.toDTO(), name = this.name)

fun PlayerDTO.toDomain(): Player = Player(id = this.id.toDomain(), name = this.name)


fun Id.toDTO(): IdDTO = IdDTO(id = this.id)

fun IdDTO.toDomain(): Id = Id(this.id)