package pl.uwr.server.external
import pl.droidsonroids.jspoon.annotation.Selector

data class SuccessfulAuthorizationPage(
        @Selector(".text > p:nth-child(2)") val loginMessage: String = ""
)