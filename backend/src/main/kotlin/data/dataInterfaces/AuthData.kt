package data.dataInterfaces

import domain.RefreshToken

interface AuthData {
    fun save(token: RefreshToken)
    fun findByToken(token: String): RefreshToken?
    fun deleteByToken(token: String)
}