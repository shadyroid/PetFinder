package com.softxpert.data.remote

import com.softxpert.domain.entity.responses.AuthResponse
import com.softxpert.domain.entity.responses.PetsResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface ApiService {

 @GET("animals")
 suspend fun requestPets(@QueryMap params: HashMap<String, String>): Response<PetsResponse>

 @FormUrlEncoded
 @POST("oauth2/token")
 suspend fun requestToken(@FieldMap body: HashMap<String, String>): Response<AuthResponse>


}