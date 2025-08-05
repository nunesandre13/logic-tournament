package services

import domain.Email

fun convertToHash(
    userEmail: Email,
    password: String,
): ByteArray {
    val concatString = userEmail.email + password
    return java.security.MessageDigest
        .getInstance("SHA-256")
        .digest(concatString.toByteArray())
}

fun verifyHashEquals(
    userEmail: Email,
    password: String,
    hash: ByteArray,
): Boolean {
    val concatString = userEmail.email + password
    val newHashBytes =
        java.security.MessageDigest
            .getInstance("SHA-256")
            .digest(concatString.toByteArray())
    return newHashBytes.contentEquals(hash)
}