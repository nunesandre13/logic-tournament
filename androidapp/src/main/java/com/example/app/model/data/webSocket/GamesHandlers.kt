package com.example.app.model.data.webSocket

import android.util.Log
import domain.games.GameData
import domain.games.GameEvent
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.GameActionResultDTO
import dto.GameDTO
import dto.GameResponse
import dto.GamesMessage
import dto.HeartBeat
import dto.MatchResultDTO
import dto.WsProtocol
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import mappers.IGameMappers

class GamesHandlers(private val mappers: IGameMappers) {

    private val _events = MutableSharedFlow<GameEvent>()
    val events: SharedFlow<GameEvent> = _events

    private val _data = MutableSharedFlow<GameData>()
    val data: SharedFlow<GameData> = _data

    suspend fun onGameResponse(data: GameResponse) {
        when (data) {
            is GameDTO -> with(mappers) { _events.emit(toDomain(data)) }
            is GameActionResultDTO -> with(mappers) { _data.emit(toDomain(data)) }
            is GamesMessage -> {}
            is MatchResultDTO -> with(mappers) { _events.emit(toDomain(data)) }
        }
    }

    suspend fun onProtocol(protocol: WsProtocol) {
        when (protocol) {
            is ConnectionClosed ->  {}
            is ConnectionOpened -> {}
            is HeartBeat -> Log.d("HeartBeat", "HeartBeat")
        }
    }
}