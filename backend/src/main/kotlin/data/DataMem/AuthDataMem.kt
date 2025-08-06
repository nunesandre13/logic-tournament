package data.DataMem

import data.dataInterfaces.AuthData
import domain.RefreshToken

class AuthDataMem : AuthData {
    private val tokens = mutableMapOf<String, RefreshToken>()

    override fun save(token: RefreshToken) {
        tokens[token.token] = token
    }

    override fun findByToken(token: String): RefreshToken? {
        return tokens[token]
    }

    override fun deleteByToken(token: String) {
        tokens.remove(token)
    }
}