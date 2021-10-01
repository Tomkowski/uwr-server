package pl.uwr.server.external

/**
 * klasa reprezentująca odpowiedź na żądanie zalogowania się przez użytkownika
 * @param statusCode status żądania (200 dla poprawnego logowania)
 * @param username login użytkownika
 * @param authenticatorToken wygenerowany token użytkownika
 */
data class LoginResponse(var statusCode: Int = 0,var username: String = "", var authenticatorToken: String = "")