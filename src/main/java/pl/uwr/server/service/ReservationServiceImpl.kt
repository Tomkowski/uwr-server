package pl.uwr.server.service

import org.springframework.stereotype.Service
import pl.uwr.server.model.Reservation
import pl.uwr.server.model.ReservationDTO
import pl.uwr.server.repository.ReservationRepository

@Service
class ReservationServiceImpl(private val reservationRepository: ReservationRepository) : ReservationApiService{
    override fun deleteReservation(id: Long): Boolean {
        reservationRepository.deleteById(id)
        return true
    }

    override fun addReservation(reservation: Reservation): Boolean {
        val reservationsThatOverlap = reservationRepository.findAllBetween(
                reservation.classId,
                reservation.beginDate,
                reservation.endDate)
        return if(reservationsThatOverlap.isEmpty()) {
            reservationRepository.save(reservation)
            true
        }
        else false
    }

    override fun findById(id: Long): Reservation? {
        return reservationRepository.findById(id).orElse(null)
    }

    override fun fetchAll(): MutableList<ReservationDTO> {
        return reservationRepository.findAll().map {
            ReservationDTO(it.classId, it.student.id, it.title, it.beginDate, it.endDate)
        } as MutableList
    }

}