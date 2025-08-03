package services

import GameFactory
import data.dataInterfaces.Data
import services.ServicesInterfaces.IGameServices
import services.ServicesInterfaces.IServices
import services.gameServices.GameRoomManager
import services.gameServices.GameService
import services.gameServices.MatchMakingService
import services.userService.UserServices

class Services(data: Data): IServices {
    override val userServices: UserServices = UserServices(data.userData)
    private val gameFactory = GameFactory()
    private val gameRoomManager = GameRoomManager()
    override val gameService: GameService = GameService(userServices,MatchMakingService(gameRoomManager,gameFactory))
}