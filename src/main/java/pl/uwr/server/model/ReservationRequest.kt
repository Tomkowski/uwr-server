package pl.uwr.server.model

import lombok.NoArgsConstructor

@NoArgsConstructor
/**
 * klasa reprezentująca żądanie rezerwacji sali przez użytkownika
 * @param requester dane logowania użytkownika dokonującego rezerwacji
 * @param classId numer sali
 * @param title tytuł rezerwacji
 * @param beginDate termin rozpoczęcia rezerwacji
 * @param endDate termin końca rezerwacji
 */
data class ReservationRequest(var requester: Credentials,
                              var classId: Long,
                              var title: String,
                              var beginDate: Long,
                              var endDate: Long)