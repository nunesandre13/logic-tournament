package com.example.app.view.userViews

import com.example.app.view.Screens

sealed class AuthScreenState {
    class Login(val nav : Screens = Screens.LOGIN) : AuthScreenState()
    class CreateAccount(val nav : Screens = Screens.REGISTER ): AuthScreenState()
}