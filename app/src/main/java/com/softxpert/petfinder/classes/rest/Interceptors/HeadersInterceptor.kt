package com.softxpert.petfinder.classes.rest.Interceptors

import com.softxpert.petfinder.classes.others.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        if (PreferencesHelper.isLoggedIn) requestBuilder.addHeader("Authorization", "Bearer "+PreferencesHelper.authToken)
        return chain.proceed(requestBuilder.build())
    }


}