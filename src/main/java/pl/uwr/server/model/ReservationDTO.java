package pl.uwr.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Calendar;


@Data
public class ReservationDTO {
    private Long classId;
    private Long studentId;
    private String title;
    private Calendar beginDate;
    private Calendar endDate;

    public ReservationDTO(Long classId, Long studentId, String title, Calendar beginDate, Calendar endDate) {
        this.classId = classId;
        this.studentId = studentId;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

}
