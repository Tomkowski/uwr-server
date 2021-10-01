package pl.uwr.server.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
/**
 * klasa reprezentująca obiekty przechowywane w repozytorium rezerwacji
 * @param classId numer sali
 * @param title tytuł rezerwacji
 * @param beginDate termin początku rezerwacji
 * @param endDate temin końca rezerwacji
 */
class Reservation(var classId: Long,
                  var title: String,
                  var beginDate: Calendar,
                  var endDate: Calendar,
                  @JsonIgnore @ManyToOne var student: Student) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            /**
             * ID rezerwacji
             */
    var id: Long = 0L
    @Column(name = "active")
            /**
             * czy rezerwacja jest aktywna
             */
    var isActive = true
    /**
     * opcjonalne pełne imię i nazwisko rezerwującego. Wykorzystywane przy rezerwacjach cyklicznych
     */
    val studentFullName = ""
}