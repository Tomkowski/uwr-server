package pl.uwr.server

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pl.uwr.server.model.Reservation
import pl.uwr.server.model.Student
import pl.uwr.server.model.Token
import pl.uwr.server.repository.ReservationRepository
import pl.uwr.server.repository.StudentRepository
import pl.uwr.server.repository.TokenRepository
import java.time.DayOfWeek
import java.util.*
import kotlin.jvm.Throws

@Component
/**
 * klasa posiadająca metodę wykonywaną po wystartowaniu serwera
 */
class BootstrapData(private val studentRepository: StudentRepository,private val tokenRepository: TokenRepository) : CommandLineRunner {

    /**
     * metoda uruchamiana po starcie serwera. Może służyć do inicjalizacji zmiennych początkowych
     */
    override fun run(vararg args: String) {

        println("App has started!")
    }

}