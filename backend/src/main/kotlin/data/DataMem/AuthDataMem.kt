package data.DataMem

import data.dataInterfaces.AuthData
import domain.Email
import domain.RefreshToken

class AuthDataMem : AuthData {

    override fun save(token: RefreshToken) {
        DataMemMemory.authTable[Email(token.userEmail)] = token
    }

    override fun findByToken(token: String): RefreshToken? {
        return DataMemMemory.authTable.values.firstOrNull { it.token == token }
    }

    override fun deleteByToken(token: String) {
        DataMemMemory.authTable.entries.find { it.value.token == token }?.also { row ->
            DataMemMemory.authTable.remove(Email(row.value.userEmail))
        }
    }
}