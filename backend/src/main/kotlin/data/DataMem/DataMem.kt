package data.DataMem

import data.dataInterfaces.AuthData
import data.dataInterfaces.Data
import data.dataInterfaces.UserData

class DataMem : Data {
    override val userData: UserData = UserDataMem()
    override val authData: AuthData = AuthDataMem()
}