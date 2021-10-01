package pl.uwr.server.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.uwr.server.model.Student

@Repository
/**
 * repozytorium przechowujące informacje o studentach
 */
interface StudentRepository : CrudRepository<Student, Long>
