package pl.uwr.server.repository;

import org.springframework.data.repository.CrudRepository;
import pl.uwr.server.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
