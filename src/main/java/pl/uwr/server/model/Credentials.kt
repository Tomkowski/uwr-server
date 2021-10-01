package pl.uwr.server.model

import lombok.NoArgsConstructor

@NoArgsConstructor
/**
 * klasa reprezentująca dane logowania użytkownika
 * @param username login użytkownika
 * @param password hasło/token użytkownika
 */
data class Credentials (
    var username: String,
    var password: String
    )