package domain.error

sealed class DomainError(val code: Int, val error: String, val defaultMessage: String) {
    object InvalidCredentials : DomainError(1001, "InvalidCredentials", "Credenciais inválidas")
    object GameAlreadyFinished : DomainError(2003, "GameAlreadyFinished", "O jogo já terminou")
    object Unknown : DomainError(5000, "UnknownError", "Erro inesperado")
}


val errorMap: Map<Int, DomainError> by lazy {
    DomainError::class.sealedSubclasses
        .map { it.objectInstance!! }
        .associateBy { it.code }
}

// Uso
val error = errorMap[1001] ?: DomainError.Unknown