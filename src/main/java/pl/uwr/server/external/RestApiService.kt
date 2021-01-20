package pl.uwr.server.external
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val retrofit = ServiceBuilder.buildService(RestApi::class.java)

class RestApiService {
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