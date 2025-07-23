package domain

sealed class CancellationMatchMakingResult {
    data object Cancelled : CancellationMatchMakingResult()
    data object ImpossibleToCancel : CancellationMatchMakingResult()
}