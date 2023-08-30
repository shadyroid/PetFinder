package com.softxpert.petfinder.classes.rest

import com.softxpert.petfinder.classes.rest.models.responses.AuthResponse
import com.softxpert.petfinder.classes.rest.models.responses.PetsResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("animals")
    suspend fun requestPets(@QueryMap params: HashMap<String, String>): Response<PetsResponse>

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun requestToken(@FieldMap body: HashMap<String, String>): Response<AuthResponse>


}