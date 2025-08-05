package data.DataMem

import data.dataInterfaces.AuthData
import domain.RefreshToken

class AuthDataMem: AuthData {
    override fun save(token: RefreshToken) {
        TODO("Not yet implemented")
    }

    override fun findByToken(token: String): RefreshToken? {
        TODO("Not yet implemented")
    }

    override fun deleteByToken(token: String) {
        TODO("Not yet implemented")
    }
}