package pl.uwr.server.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.uwr.server.model.Reservation
import java.util.*

@Repository
/**
 * repozytorium przechowujące rezerwacje użytkowników
 */
interface ReservationRepository : CrudRepository<Reservation, Long> {
    @Query("""SELECT r FROM Reservation r 
        WHERE r.isActive = true 
        AND r.classId = :classId
        AND r.beginDate < :endDate
        AND r.endDate > :beginDate""")
            /**
             * zwraca wszystkie rezerwacje z przedziału [beginDate] do [endDate] dla sali [classId]
             * @param classId numer sali
             * @param beginDate początek okresu
             * @param endDate koniec okresu
             */
    fun findAllBetween(classId: Long, beginDate: Calendar, endDate: Calendar): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE r.isActive = true")
            /**
             * zwraca wszystkie aktywne rezerwacje
             */
    fun findAllActive(): List<Reservation>
}