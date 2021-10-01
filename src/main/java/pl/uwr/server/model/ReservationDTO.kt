package pl.uwr.server.model

import lombok.NoArgsConstructor

/**
 * klasa reprezentująca rezerwacje z usuniętymi danymi wrażliwymi takimi jak token rezerwującego.
 */
@NoArgsConstructor
data class ReservationDTO(val id: Long,
                          val classId: Long,
                          val studentId: Long,
                          val studentFullName: String,
                          val title: String,
                          val beginDate: Long,
                          val endDate: Long
)