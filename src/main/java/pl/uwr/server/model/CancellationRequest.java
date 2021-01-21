package pl.uwr.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CancellationRequest {
    private Credentials requester;
    private Long classId;

    public CancellationRequest(Credentials requester, Long classId) {
        this.requester = requester;
        this.classId = classId;
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
}
