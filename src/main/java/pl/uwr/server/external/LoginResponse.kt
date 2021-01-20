package pl.uwr.server.external

data class LoginResponse(var statusCode: Int = 0,var username: String = "", var authenticatorToken: String = "")