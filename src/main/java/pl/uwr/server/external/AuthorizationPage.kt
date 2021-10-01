package pl.uwr.server.external
import pl.droidsonroids.jspoon.annotation.Selector

/**
 * klasa reprezentująca odpowiedź serwisu CAS na podane login i hasło użytkownika
 */
data class AuthorizationPage(
        /**
         * wiadomość wyświetlana na stronie po zalogowaniu użytkownika
         */
        @Selector(".text > p:nth-child(2)") val loginMessage: String = ""
)


