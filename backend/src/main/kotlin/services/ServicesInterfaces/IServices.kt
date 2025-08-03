package services.ServicesInterfaces

import services.userService.UserServices


interface IServices {
     val userServices: UserServices
     val gameService: IGameServices
}