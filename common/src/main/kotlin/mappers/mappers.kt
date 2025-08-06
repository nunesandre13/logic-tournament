
import domain.Id
import domain.Player
import domain.Tokens
import domain.User
import dto.IdDTO
import dto.PlayerDTO
import dto.TokenDTO
import dto.UserOUT


// player mappers
fun Player.toDTO(): PlayerDTO = PlayerDTO(id = this.id.toDTO(), name = this.name)

fun PlayerDTO.toDomain(): Player = Player(id = this.id.toDomain(), name = this.name)


// id mappers
fun Id.toDTO(): IdDTO = IdDTO(id = this.id)

fun IdDTO.toDomain(): Id = Id(this.id)


// USER mappers
fun User.toOUT() = UserOUT(this.name, this.id.toDTO(), this.email.email)

fun Tokens.toDTO(): TokenDTO = TokenDTO(this.accessToken,this.refreshToken)

