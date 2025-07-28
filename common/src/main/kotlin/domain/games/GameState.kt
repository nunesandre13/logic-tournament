package domain.games

enum class GameState {
    WAITING,   // à espera de jogadores, ou do início
    RUNNING,   // jogo a decorrer
    FINISHED,  // jogo terminado (por vitória, empate, etc.)
    CANCELLED  // jogo interrompido antes do fim (opcional)
}