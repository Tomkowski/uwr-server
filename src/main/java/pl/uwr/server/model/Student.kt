package pl.uwr.server.model

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
/**
 * klasa reprezentująca obiekty przechowywane w repozytorium rezerwacji
 */
class Student {
    @Id
            /**
             * ID studenta (jego login)
             */
    var id: Long
    @OneToOne(mappedBy = "student", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
            /**
             * wygenerowany token studenta
             */
    var token: Token
    @OneToMany(mappedBy = "student")
            /**
             * lista rezerwacji studenta
             */
    val reservationList = mutableListOf<Reservation>()
    /**
     * pełne imię i nazwisko studenta. Wykorzystywane do rezerwacji cyklicznych
     */
    var fullName: String

    /**
     * konstruktor przyjmujący login użytkownika i wygenerowany token
     * @param id login użytkownika
     * @param token wygenerowany token przypisany do użytkownika
     */
    constructor(id: Long, token: Token) {
        this.id = id
        this.token = token
        fullName = ""
    }

    /**
     * konstruktor przyjmujący login użytkownika, wygenerowany token i imię użytkownika
     * @param id login użytkownika
     * @param token wygenerowany token przypisany do użytkownika
     * @param fullName imię i nazwisko użytkownika
     */
    constructor(id: Long, token: Token, fullName: String) {
        this.id = id
        this.token = token
        this.fullName = fullName
    }

    constructor(fullName: String) {
        this.id = -1L
        this.fullName = fullName
        token = Token(-1L, Calendar.getInstance(), "")
    }


}