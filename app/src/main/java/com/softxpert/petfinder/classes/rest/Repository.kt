package com.softxpert.petfinder.classes.rest

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.softxpert.petfinder.classes.rest.Interceptors.HeadersInterceptor
import com.softxpert.petfinder.classes.others.CONSTANTS
import com.softxpert.petfinder.classes.rest.models.beans.TypeBean
import com.softxpert.petfinder.classes.rest.models.responses.AuthResponse
import com.softxpert.petfinder.classes.rest.models.responses.PetsResponse
import com.softxpert.petfinder.classes.rest.models.responses.TypesResponse
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Repository {
    private lateinit var apiInterface: ApiInterface
    fun anInterface(): ApiInterface {
        if (!::apiInterface.isInitialized) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(PrettyLogger())
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val headersInterceptor = HeadersInterceptor()
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(headersInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
            apiInterface = Retrofit.Builder()
                .baseUrl(CONSTANTS.BACKEND.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
        return apiInterface
    }

    suspend fun requestTypes(): TypesResponse {
        return TypesResponse.builder()
            .types(
                mutableListOf(
                    TypeBean.builder()
                        .name("All")
                        .build(),
                    TypeBean.builder()
                        .name("Cat")
                        .build(),
                    TypeBean.builder()
                        .name("Horse")
                        .build(),
                    TypeBean.builder()
                        .name("Bird")
                        .build(),
                    TypeBean.builder()
                        .name("Rabbit")
                        .build(),

                    )
            )
            .build()
    }

    suspend fun requestPets(body : HashMap<String, String>): Response<PetsResponse> {
        return anInterface().requestPets(body)
    }
    suspend fun requestToken(body : HashMap<String, String>): Response<AuthResponse> {
        return anInterface().requestToken(body)
    }


    internal class PrettyLogger : HttpLoggingInterceptor.Logger {
        private val mGson = GsonBuilder().setPrettyPrinting().create()
        private val mJsonParser = JsonParser()
        override fun log(message: String) {
            val trimMessage = message.trim { it <= ' ' }
            if (trimMessage.startsWith("{") && trimMessage.endsWith("}") || trimMessage.startsWith("[") && trimMessage.endsWith(
                    "]"
                )
            ) {
                try {
                    val prettyJson = mGson.toJson(mJsonParser.parse(message))
                    Platform.get().log(prettyJson, 1, null)
                } catch (e: Exception) {
                    Platform.get().log(message, 2, e)
                }
            } else {
                Platform.get().log(message, 3, null)
            }
        }
    }
}