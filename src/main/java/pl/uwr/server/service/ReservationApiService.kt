package pl.uwr.server.service

import pl.uwr.server.model.Reservation
import pl.uwr.server.model.ReservationDTO

interface ReservationApiService {

    fun fetchAll(): List<ReservationDTO?>?
    fun findById(id: Long): Reservation?
    fun addReservation(reservation: Reservation): Boolean
    fun deleteReservation(id: Long): Boolean

}