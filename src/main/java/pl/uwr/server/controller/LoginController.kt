package pl.uwr.server.controller

import lombok.NoArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.droidsonroids.jspoon.Jspoon
import pl.uwr.server.external.*
import pl.uwr.server.model.Credentials
import pl.uwr.server.model.Student
import pl.uwr.server.model.Token
import pl.uwr.server.repository.StudentRepository
import pl.uwr.server.repository.TokenRepository
import java.security.SecureRandom
import java.util.*
import kotlin.concurrent.thread

@Component
@RestController
@RequestMapping("/authorize")
@NoArgsConstructor
/**
 * Kontroler odpowiadający na żądania użytkowników dotyczące logowania i wylogowywania
 * @param studentRepository repozytorium studentów
 * @param tokenRepository repozytorium z tymczasowymi tokenami
 */
class LoginController(private val studentRepository: StudentRepository, private val tokenRepository: TokenRepository) {
    /**
     * pliki cookies wykorzystywane przez stronę logowania CAS
     */
    var cookies = ""
    var logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)

    /**
     * metoda logująca użytkownika przy pomocy loginu i hasła
     * @param credentials obiekt JSON z loginem i hasłem użytkownika
     */
    @PostMapping
    fun performLogin(@RequestBody credentials: Credentials): LoginResponse {
        val service = RestApiService()
        val loginResponse = LoginResponse()

        with(credentials) {
            if (username == null || password == null) {
                return LoginResponse(414, "missing username", "")
            }
        }

        service.getHeaders(cookies) { response ->
            if (response == null) {
                loginResponse.statusCode = 414
                return@getHeaders
            }
            cookies = response.headers().get("Set-Cookie") ?: ""

            val loginPage = Jspoon.create().adapter(LoginPage::class.java).fromHtml(response.body())

            val loginPostObject = LoginPostObject(
                    username = credentials.username,
                    password = credentials.password,
                    lt = loginPage.lt,
                    execution = loginPage.execution
            )

            service.sendCredentials(cookies, loginPostObject) { response ->
                if (response == null) {
                    loginResponse.statusCode = 414
                    return@sendCredentials
                }
                val loginSuccessfulPage =
                        Jspoon.create().adapter(AuthorizationPage::class.java).fromHtml(
                                response.body()
                        )
                if (loginSuccessfulPage.loginMessage == "Zalogowałeś się w CAS - Centralnej Usłudze Uwierzytelniania.") {
                    val tokenContent = generateNewToken() ?: return@sendCredentials

                    with(loginResponse) {
                        statusCode = 200
                        username = credentials.username
                        authenticatorToken = tokenContent

                        val token = Token(username.toLong(), Calendar.getInstance(), authenticatorToken)
                        val optional = studentRepository.findById(username.toLong())
                        val student = if (optional.isPresent) optional.get().apply { this.token = token } else Student(username.toLong(), token)
                        token.student = student
                        studentRepository.save(student)
                        tokenRepository.save(student.token)
                    }
                } else {
                    loginResponse.statusCode = 414
                }
            }
        }

        thread {
            while (loginResponse.statusCode == 0) {
                Thread.sleep(17)
            }
        }.join()
        return loginResponse
    }

    /**
     * metoda logująca użytkownika przy pomocy loginu i tokenu
     * @param credentials obiekt JSON z loginem i tokenem użytkownika
     */
    @PostMapping("/token")
    fun performLoginWithToken(@RequestBody credentials: Credentials): LoginResponse {
        val requestUsername = credentials.username
        val requestToken = credentials.password

        val student = studentRepository.findById(requestUsername.toLong())
        var response: LoginResponse = LoginResponse(414, requestUsername, requestToken)
        student.ifPresent {
            if (it.token.content == requestToken) {
                response = LoginResponse(200, "${it.id}", it.token.content)
                logger.info("$requestUsername has logged in using token: $requestToken")
            }
        }
        return response
    }

    /**
     * metoda wylogowująca użytkownika przy pomocy loginu i tokenu
     * @param credentials obiekt JSON z loginem i tokenem użytkownika
     */
    @PostMapping("/logout")
    fun performLogout(@RequestBody credentials: Credentials): String {
        val requestUsername = credentials.username
        val requestToken = credentials.password

        val student = studentRepository.findById(requestUsername.toLong())
        var response = "400"
        student.ifPresent {
            if (it.token.content == requestToken) {
                if(requestUsername in listOf("-1")) {
                    //ADMIN CASE
                    response = "200"
                    return@ifPresent
                }
                      response = "200"
                it.token.content = "INVALID"
                it.token.validity = Calendar.getInstance()

                studentRepository.save(it)
                tokenRepository.save(it.token)
            }
        }
        return response
    }

    /**
     * logowanie do aplikacji jako administrator
     * @param credentials login i hasło administratora
     */
    @PostMapping("/admin")
    fun performAdminLogin(@RequestBody credentials: Credentials): LoginResponse {
        val requestUsername = credentials.username
        val requestToken = credentials.password
        println("received $credentials")
        val student = studentRepository.findById(requestUsername.toLong())
        var response = 400
        student.ifPresent {
            if(it.id in listOf(-1L))
                if (it.token.content == requestToken) {
                response = 200
            }
        }
        return LoginResponse(response, requestUsername, requestToken)
    }

    /**
     * generator liczb pseudolosowych
     */
    private val secureRandom: SecureRandom = SecureRandom()
    /**
     * konwerter bajtów do łańcuchów znakowych
     */
    private val base64Encoder = Base64.getUrlEncoder()

    /**
     * metoda zwracająca 24-bajtowe tokeny złożone z losowych bajtów
     */
    private fun generateNewToken(): String? {
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        return base64Encoder.encodeToString(randomBytes)
    }
}