package com.shady.data.remote

import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UsersResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface ApiService {

    @FormUrlEncoded
    @POST("/pos/login")
    suspend fun requestLogin(@FieldMap body: HashMap<String, String>): LoginResponse
    @GET("/pos/invoice")
    suspend fun requestInvoices(@QueryMap params: HashMap<String, String>): InvoicesResponse
    @GET("/pos/invoices/paid")
    suspend fun requestReceipts(@QueryMap params: HashMap<String, String>): ReceiptsResponse

    @GET("/pos/search")
    suspend fun requestUsers(@QueryMap params: HashMap<String, String>): UsersResponse

    @GET("/pos/building")
    suspend fun requestBuildings(): BuildingsResponse


}