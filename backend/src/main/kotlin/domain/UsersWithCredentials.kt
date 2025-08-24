package domain

data class UsersWithCredentials(val user: User, val passwordHash: ByteArray)