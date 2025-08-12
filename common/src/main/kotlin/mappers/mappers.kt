
import domain.Email
import domain.Id
import domain.Player
import domain.Tokens
import domain.User
import domain.UserAuthResponse
import dto.IdDTO
import dto.PlayerDTO
import dto.TokensDTO
import dto.UserAuthDTO
import dto.UserOUT


// player mappers
fun Player.toDTO(): PlayerDTO = PlayerDTO(id = this.id.toDTO(), name = this.name)

fun PlayerDTO.toDomain(): Player = Player(id = this.id.toDomain(), name = this.name)


// id mappers
fun Id.toDTO(): IdDTO = IdDTO(id = this.id)

fun IdDTO.toDomain(): Id = Id(this.id)


// USER mappers
fun User.toOUT() = UserOUT(this.name, this.id.toDTO(), this.email.email)
fun UserOUT.toDomain() = User(this.id.toDomain(),this.name, Email(this.email))

fun Tokens.toDTO(): TokensDTO = TokensDTO(this.accessToken,this.refreshToken)

fun TokensDTO.toDomain(): Tokens = Tokens(this.accessToken,this.refreshToken)

fun UserAuthDTO.toDomain(): UserAuthResponse = UserAuthResponse(this.user.toDomain(), this.tokensDTO.toDomain())

