package pl.uwr.server.external

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * obiekt tworzący instancję Retrofit
 */
object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    /**
     * obiekt Retrofit mapujący elementy strony HTML serwisu CAS do obiektu JSON
     */
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://login.uni.wroc.pl/cas/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()

    /**
     * metoda zwracająca gotowy do używania obiekt Retrofit
     * @param T interfejs serwisu
     * @param service serwis API
     */
    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}