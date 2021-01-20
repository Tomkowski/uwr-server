package pl.uwr.server.controller

import lombok.NoArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
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
class LoginController(val studentRepository: StudentRepository, val tokenRepository: TokenRepository) {
    var cookies = ""

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
                //println(response?.body().toString())
                if (response == null) {
                    loginResponse.statusCode = 414
                    return@sendCredentials
                }
                val loginSuccessfulPage =
                        Jspoon.create().adapter(SuccessfulAuthorizationPage::class.java).fromHtml(
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

    @PostMapping("/token")
    fun performLoginWithToken(@RequestBody credentials: Credentials): LoginResponse {
        val requestUsername = credentials.username
        val requestToken = credentials.password

        val student = studentRepository.findById(requestUsername.toLong())
        var response: LoginResponse = LoginResponse(414, requestUsername, requestToken)
        student.ifPresent {
            if (it.token.content == requestToken) {
                response = LoginResponse(200, "${it.id}", it.token.content)
                println("$requestUsername has logged in using token: $requestToken")
            }
        }
        return response
    }

    @PostMapping("/logout")
    fun performLogout(@RequestBody credentials: Credentials): String {
        val requestUsername = credentials.username
        val requestToken = credentials.password

        val student = studentRepository.findById(requestUsername.toLong())
        var response = "400"
        student.ifPresent {
            if (it.token.content == requestToken) {
                response = "200"
                it.token.content = "INVALID"
                it.token.validity = Calendar.getInstance()

                studentRepository.save(it)
                tokenRepository.save(it.token)
            }
        }
        return response
    }


    private val secureRandom: SecureRandom = SecureRandom()
    private val base64Encoder = Base64.getUrlEncoder()

    private fun generateNewToken(): String? {
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        return base64Encoder.encodeToString(randomBytes)
    }
}