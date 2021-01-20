package pl.uwr.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@Entity
@ToString
public class Token {

    @Id
    @Column(name = "tokenId")
    private Long id;
    private Calendar validity;
    @OneToOne
    @MapsId
    @JoinColumn(name = "tokenId")
    private Student student;
    private String content;

    public Token(Long id , Calendar validity, String content){
        this.id = id;
        this.validity = validity;
        this.content = content;

        //Koniec ważności tokenu za miesiąc o 4:00 rano
        validity.add(Calendar.MONTH, 1);
        validity.set(Calendar.HOUR, -8);
        validity.set(Calendar.MINUTE, 0);
        validity.set(Calendar.SECOND, 0);
        validity.set(Calendar.MILLISECOND, 0);
    }

    public Calendar getValidity() {
        return validity;
    }

    public void setValidity(Calendar validity) {
        this.validity = validity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
