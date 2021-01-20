package pl.uwr.server.external

import retrofit2.Call
import retrofit2.http.*

interface RestApi {

    @GET("login")
    fun getHeaders(@Header("Cookie") cookies: String): Call<String>

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