package pl.uwr.server.external

/**
 * klasa reprezentująca żądanie logowania wysyłane do serwera CAS
 * @param username login użytkownika
 * @param password hasło użytkownika
 */
data class LoginPostObject(
        val username: String,
        val password: String,
        val lt: String,
        val execution: String
)