package pl.uwr.server.external
import pl.droidsonroids.jspoon.annotation.Selector

data class LoginPage(
        @Selector("#form > input:nth-child(4)", attr = "value") val lt: String = "",
        @Selector("#form > input:nth-child(5)", attr = "value") val execution: String = ""
)