package pl.uwr.server.external

data class LoginPostObject(
        val username: String,
        val password: String,
        val lt: String,
        val execution: String
)