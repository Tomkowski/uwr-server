package pl.uwr.server.model;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long id;
    private Long classId;
    private Long studentId;
    private String title;
    private Long beginDate;
    private Long endDate;

    public ReservationDTO(Long id, Long classId, Long studentId, String title, Long beginDate, Long endDate) {
        this.id = id;
        this.classId = classId;
        this.studentId = studentId;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

}
