package pl.uwr.server.model

import lombok.NoArgsConstructor

@NoArgsConstructor
/**
 * klasa reprezentująca żądanie anulowania rezerwacji przez użytkownika
 * @param requester dane użytkownika anulującego rezerwację
 * @param reservationId ID rezerwacji
 */
class CancellationRequest(var requester: Credentials, var reservationId: Long)