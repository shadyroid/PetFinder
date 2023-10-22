package com.shady.data.remote

import com.shady.domain.entity.responses.AuthResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.entity.responses.UsersResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface ApiService {

 @FormUrlEncoded
 @POST("/users/login")
 suspend fun requestLogin(@FieldMap body: HashMap<String, String>): LoginResponse

 @GET("/receipts/receiver")
 suspend fun requestInvoices(@QueryMap params: HashMap<String, String>): InvoicesResponse
 @GET("/receipts/receiver")
 suspend fun requestUsers(@QueryMap params: HashMap<String, String>): UsersResponse



}