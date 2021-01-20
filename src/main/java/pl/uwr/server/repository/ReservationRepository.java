package pl.uwr.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.uwr.server.model.Reservation;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.classId = :classId AND r.beginDate < :endDate AND r.endDate > :beginDate")
    List<Reservation> findAllBetween(Long classId, Calendar beginDate, Calendar endDate);
}
