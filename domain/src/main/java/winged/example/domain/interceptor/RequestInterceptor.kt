package winged.example.domain.interceptor

import android.content.Context

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import winged.example.domain.R
import java.io.IOException
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val appContext: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        try {
            val response = chain.proceed(request)
            val bodyString = response.body
            return response.newBuilder().body(bodyString!!).build()
        } catch (e: Exception) {
            val errorMessage: String = when (e) {
                is HttpException -> appContext.getString(R.string.httpExceptionMessage)
                is IOException -> appContext.getString(R.string.ioExceptionMessage)
                else -> appContext.getString(R.string.unknownExceptionMessage, e.message)
            }
            return Response
                .Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body("".toResponseBody(null)) // error, no additional data is needed
                .code(400)
                .message(errorMessage)
                .build()
        }
    }
}
