package com.shady.data.remote.interceptors

import com.shady.data.preferenceses.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor(private val preferencesHelper: PreferencesHelper) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        requestBuilder.addHeader(
            "Content-Type",
            "application/json; charset=UTF-8"
        )
        requestBuilder.addHeader(
            "Accept",
            "application/json; charset=UTF-8"
        )
        preferencesHelper.authToken?.let {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $it"
            )
        }
        return chain.proceed(requestBuilder.build())
    }


}