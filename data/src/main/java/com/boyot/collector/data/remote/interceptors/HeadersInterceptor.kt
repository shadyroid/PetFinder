package com.boyot.collector.data.remote.interceptors

import com.boyot.collector.data.preferenceses.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor(private val preferencesHelper: PreferencesHelper) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        preferencesHelper.getAuthToken()?.let {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $it"
            )
        }
        return chain.proceed(requestBuilder.build())
    }


}