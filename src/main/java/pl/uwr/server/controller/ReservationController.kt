package pl.uwr.server.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.uwr.server.model.*
import pl.uwr.server.repository.StudentRepository
import pl.uwr.server.service.ReservationServiceImpl
import java.util.*

@RestController
@RequestMapping("/reservation")
class ReservationController(
        private val reservationService: ReservationServiceImpl,
        private val studentRepository: StudentRepository
) {

    @RequestMapping("all")
    fun fetchAll(@RequestBody credentials: Credentials): List<ReservationDTO> {
        println("${credentials.username} asked for all reservations")
        return if (validateUser(credentials)) reservationService.fetchAll() else emptyList()
    }

    @RequestMapping("add")
    fun addReservation(@RequestBody reservationRequest: ReservationRequest): String {
        val credentials = reservationRequest.requester

        if (!validateUser(credentials)) return "414"
        val studentId = credentials.username.toLong()
        val student = studentRepository.findById(studentId).get()
        with(reservationRequest) {

            val reservation = Reservation(
                    classId,
                    title,
                    Calendar.getInstance().apply { timeInMillis = beginDate },
                    Calendar.getInstance().apply { timeInMillis = endDate },
                    student)
            reservation.student = student

            return if (reservationService.addReservation(reservation)) {
                student.reservationList.add(reservation)
                studentRepository.save(student)
                "200"
            } else "400"
        }

    }
    @RequestMapping("cancel")
    fun cancelReservation(@RequestBody cancellationRequest: CancellationRequest): String{
        if(!validateUser(cancellationRequest.requester)) return "414"
        return if(reservationService.cancelReservation(cancellationRequest.classId)) "200" else "400"
    }

    private fun validateUser(credentials: Credentials): Boolean {
        val studentId = credentials.username.toLong()
        val token = credentials.password
        val student = studentRepository.findById(studentId).orElse(null) ?: return false

        return student.id == studentId && token == student.token.content
    }
}