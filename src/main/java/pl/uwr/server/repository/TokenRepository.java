package pl.uwr.server.repository;

import org.springframework.data.repository.CrudRepository;
import pl.uwr.server.model.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
