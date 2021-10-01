package pl.uwr.server.external

import retrofit2.Call
import retrofit2.http.*

/**
 * interfejs Retrofit odpowiedzialny za komunikację z serwerem CAS
 */
interface RestApi {

    /**
     * metoda pobierająca pliki cookies ze strony logowania CAS
     * @param cookies początkowo pusty string wymagany przez stronę w żądaniu
     */
    @GET("login")
    fun getHeaders(@Header("Cookie") cookies: String): Call<String>

    /**
     * metoda wysyłająca żądanie zalogowania się do serwisu CAS
     * @param cookies ciasteczka użytkownika
     * @param username login użytkownika
     * @param password hasło użytkownika
     */
    @FormUrlEncoded
    @POST("login")
    fun sendCredentials(
            @Header("Cookie") cookies: String,
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("lt") lt: String,
            @Field("execution") execution: String,
            @Field("_eventId") eventId: String = "submit",
            @Field("submit") submit: String = "ZALOGUJ"
    ) : Call<String>

}