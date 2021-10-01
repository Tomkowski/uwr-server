package pl.uwr.server.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.uwr.server.model.*
import pl.uwr.server.repository.StudentRepository
import pl.uwr.server.service.ReservationServiceImpl
import java.util.*

@RestController
@RequestMapping("/reservation")
/**
 * Kontroler odpowiadający na żądania użytkowników dotyczące rezerwacji
 * @param reservationService serwis odpowiedzialny za operowanie na repozytorium rezerwacji
 * @param studentRepository repozytorium studentów
 */
class ReservationController(
        private val reservationService: ReservationServiceImpl,
        private val studentRepository: StudentRepository
) {
    /**
     * logger do zapisywania aktywności na serwerze
     */
    private val logger: Logger = LoggerFactory.getLogger(ReservationController::class.java)

    /**
     * metoda zwracająca wszystkie aktywne rezerwacje
     * @param credentials obiekt JSON z loginem i tokenem użytkownika
     */
    @PostMapping("all")
    fun fetchAll(@RequestBody credentials: Credentials): MutableList<ReservationDTO> {
        logger.info("${credentials.username} asked for all reservations")
        return if (validateUser(credentials)) reservationService.fetchAll() else mutableListOf()
    }

    /**
     * metoda dodająca rezerwację do repozytorium
     * @param reservationRequest żądanie użytkownika ze szczegółami rezerwacji
     */
    @RequestMapping("add")
    fun addReservation(@RequestBody reservationRequest: ReservationRequest): String {
        val credentials = reservationRequest.requester

        if (!validateUser(credentials)) return "414"
        val studentId = credentials.username.toLong()
        val student = studentRepository.findById(studentId).get()
        with(reservationRequest) {
            if(beginDate >= endDate || endDate < System.currentTimeMillis()){
                logger.info("Incorrect data. Reservation failed")
                logger.info("$beginDate $endDate ${System.currentTimeMillis()}")
                return "Nice try but no banana"
            }
            val reservation = Reservation(
                    classId,
                    title,
                    Calendar.getInstance().apply { timeInMillis = beginDate },
                    Calendar.getInstance().apply { timeInMillis = endDate },
                    student)

            return if (reservationService.addReservation(reservation)) {
                student.reservationList.add(reservation)
                studentRepository.save(student)
                logger.info("OK, reservation set $reservation")
                "200"
            } else {
                logger.info("Class is taken. Reservation failed")
                "400"
            }
        }

    }

    /**
     * metoda anulująca rezerwację użytkownika
     * @param cancellationRequest obiekt JSON zawierający szczegóły rezerwacji i dane użytkownika
     */
    @RequestMapping("cancel")
    fun cancelReservation(@RequestBody cancellationRequest: CancellationRequest): String{
        if(!validateUser(cancellationRequest.requester)) return "414"
        return if(reservationService.cancelReservation(cancellationRequest.requester.username.toLong(), cancellationRequest.reservationId)) "200" else "400"
    }

    private fun validateUser(credentials: Credentials): Boolean {
        val studentId = credentials.username.toLong()
        val token = credentials.password
        val student = studentRepository.findById(studentId).orElse(null) ?: return false

        return student.id == studentId && token == student.token.content
    }
}