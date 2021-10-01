package pl.uwr.server.external
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * gotowy klient Retrofit
 */
val retrofit = ServiceBuilder.buildService(RestApi::class.java)

/**
 * serwis udostępniający interfejs Retrofit do użytkowania po stronie serwera
 */
class RestApiService {
    /**
     * metoda pobierająca pliki cookies z serwisu CAS
     * @param cookies pliki cookies informujące o trwającej sesji użytkownika
     * @param onResult funkcja, która zostanie wykonana po otrzymaniu odpowiedzi z serwera
     */
    fun getHeaders(cookies: String, onResult: (Response<String>?) -> Unit) {
        retrofit.getHeaders(cookies).enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        onResult(null)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        onResult(response)
                    }
                }
        )
    }

    /**
     * metoda wysyłająca żądanie zalogowania się do serwisu CAS
     * @param cookies pliki cookies informujące o trwającej sesji użytkownika
     * @param loginPostObject obiekt zawierający login i hasło użytkownika oraz potrzebne pliki nagłówkowe wymagane przez serwis CAS
     * @param onResult funkcja, która zostanie wykonana po otrzymaniu odpowiedzi z serwera
     */
    fun sendCredentials(
            cookies: String,
            loginPostObject: LoginPostObject,
            onResult: (Response<String>?) -> Unit
    ) {
        with(loginPostObject) {
            retrofit.sendCredentials(cookies, username, password, lt, execution).enqueue(
                    object : Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            onResult(null)
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            onResult(response)
                        }
                    }
            )
        }
    }
}