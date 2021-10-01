package pl.uwr.server.model

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
/**
 * klasa reprezentująca obiekty przechowywane w repozytorium tokenów
 */
class Token(
        /**
         * id tokenu - login użytkownika do niego przypisany
         */
        @Column(name = "tokenId") @Id private val id: Long,
        /**
         * ważność tokenu
         */
        var validity: Calendar,
        /**
         * 24-bajtowy łańcuch znaków
         */
        var content: String) {
    @OneToOne
    @MapsId
    @JoinColumn(name = "tokenId")
    /**
     * właściciel tokenu
     */
    lateinit var student: Student

    init {
        //Koniec ważności tokenu za miesiąc o 4:00 rano
        with(validity) {
            add(Calendar.MONTH, 1)
            this[Calendar.HOUR] = -8
            this[Calendar.MINUTE] = 0
            this[Calendar.SECOND] = 0
            this[Calendar.MILLISECOND] = 0
        }
    }
}








