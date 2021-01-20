package pl.uwr.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@NoArgsConstructor
public class ReservationRequest {
    private Credentials requester;
    private Long classId;
    private String title;
    private Long beginDate;
    private Long endDate;

    public ReservationRequest(Credentials requester, Long classId, String title, Long beginDate, Long endDate) {
        this.requester = requester;
        this.classId = classId;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Credentials getRequester() {
        return requester;
    }

    public void setRequester(Credentials requester) {
        this.requester = requester;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
