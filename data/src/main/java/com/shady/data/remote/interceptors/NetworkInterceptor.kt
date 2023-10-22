package com.shady.data.remote.interceptors

import com.shady.domain.entity.responses.BaseResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class NetworkInterceptor : Interceptor {
    var gson = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            val response = chain.proceed(request)
            val body = response.body
            var bodyString = body?.string()
            val contentType = body?.contentType()
            bodyString = bodyString?.replaceFirst(
                "\\{".toRegex(),
                "{\"statusCode\": \"${response.code}\"${if (bodyString.isEmpty()) "}" else ","}"
            )
            if (response.code == 200 || response.code == 201 || response.code == 202) {
                response.newBuilder().body(bodyString?.toResponseBody(contentType)).build()
            } else {
                response.newBuilder()
                    .body(bodyString?.toResponseBody(contentType)).code(200)
                    .build()
            }
        } catch (e: IOException) {
            handleException(e, request, 0)
        } catch (e: Exception) {
            handleException(e, request, 1)
        }
    }

    private fun handleException(e: Exception, request: Request, statusCode: Int): Response {
        val baseResponse = BaseResponse()
        baseResponse.statusCode = statusCode
        baseResponse.details = e.toString()
        return Response.Builder()
            .body(
                gson.toJson(
                    baseResponse
                )
                    .toResponseBody(null)
            )
            .protocol(Protocol.HTTP_2)
            .message("")
            .request(request)
            .code(200)
            .build()
    }


}