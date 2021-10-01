package pl.uwr.server.service

import org.springframework.stereotype.Service
import pl.uwr.server.model.Reservation
import pl.uwr.server.model.ReservationDTO
import pl.uwr.server.repository.ReservationRepository

@Service
/**
 * implementacja interfejsu [ReservationApiService]
 * @param reservationRepository repozytorium przechowujące rezerwacje użytkowników
 */
class ReservationServiceImpl(private val reservationRepository: ReservationRepository) : ReservationApiService {
    /**
     * usuwa rezerwację z bazy danych
     * @param id ID rezerwacji
     */
    override fun deleteReservation(id: Long): Boolean {
        reservationRepository.deleteById(id)
        return true
    }

    /**
     * dodaje rezerwację do repozytorium
     * @param reservation szczegóły rezerwacji
     */
    override fun addReservation(reservation: Reservation): Boolean {
        val reservationsThatOverlap = reservationRepository.findAllBetween(
                reservation.classId,
                reservation.beginDate,
                reservation.endDate)
        return if (reservationsThatOverlap.isEmpty()) {
            reservationRepository.save(reservation)
            true
        } else false
    }

    /**
     * zwraca rezerwację o zadanym id [id]
     * @param id ID wyszukiwanej rezerwacji
     */
    override fun findById(id: Long): Reservation? {
        return reservationRepository.findById(id).orElse(null)
    }

    /**
     * metoda zwracająca wszystkie aktywne rezerwacji w postaci [ReservationDTO]
     */
    override fun fetchAll(): MutableList<ReservationDTO> {
        return reservationRepository.findAllActive().map {
            ReservationDTO(it.id,
                    it.classId,
                    it.student.id,
                    it.studentFullName,
                    it.title,
                    it.beginDate.timeInMillis,
                    it.endDate.timeInMillis) 
        } as MutableList
    }

    fun cancelReservation(requesterID: Long, id: Long): Boolean {
        val reservation = reservationRepository.findById(id)

        if (reservation.isPresent) {
            reservation.get().let {
                //Sprawdź czy anuluje właściciel lub admin
                if(it.student.id != requesterID && !(requesterID in listOf(-1L))) return false

                it.isActive = false
                reservationRepository.save(it)
                return true
            }
        }
        return false
    }

}