package services

import domain.Email

fun convertToHash(
    password: String,
): ByteArray {
    return java.security.MessageDigest
        .getInstance("SHA-256")
        .digest(password.toByteArray())
}

fun verifyHashEquals(
    password: String,
    hash: ByteArray,
): Boolean {
    val newHashBytes =
        java.security.MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
    return newHashBytes.contentEquals(hash)
}