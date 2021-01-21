package pl.uwr.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Calendar;


@Data
public class ReservationDTO {
    private Long classId;
    private Long studentId;
    private String title;
    private Long beginDate;
    private Long endDate;

    public ReservationDTO(Long classId, Long studentId, String title, Long beginDate, Long endDate) {
        this.classId = classId;
        this.studentId = studentId;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

}
