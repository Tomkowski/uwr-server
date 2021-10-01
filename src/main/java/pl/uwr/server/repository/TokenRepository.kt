package pl.uwr.server.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.uwr.server.model.Token

@Repository
/**
 * repozytorium przechowujące informacje o tokenach użytkowników
 */
interface TokenRepository : CrudRepository<Token?, Long?>
