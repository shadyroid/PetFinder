package com.shady.data.remote

import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.entity.responses.GenerateOrderIdResponse
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.entity.responses.LoginResponse
import com.shady.domain.entity.responses.PaymentNotificationResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UnitsResponse
import com.shady.domain.entity.responses.UserDetailsResponse
import com.shady.domain.entity.responses.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
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

    @Multipart
    @POST("/pos/client/{user_id}")
    suspend fun requestEditUser(
        @Path("user_id") userId: String,
        @PartMap params: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): BaseResponse

    @GET("/pos/client/{user_id}")
    suspend fun requestUserDetails(@Path("user_id") userId: String): UserDetailsResponse

    @GET("/pos/building")
    suspend fun requestBuildings(): BuildingsResponse

    @GET("/pos/units")
    suspend fun requestUnits(@QueryMap params: HashMap<String, String>): UnitsResponse

    @POST("/pos/invoices/offlinePaid")
    suspend fun requestCashCheckout(@Body request: CheckoutRequest): CheckoutResponse

    @POST("/pos/confirmOnlinePaid")
    suspend fun requestVisaCheckout(@Body request: CheckoutRequest): CheckoutResponse

    @POST("/pos/generateOrderIds")
    suspend fun requestGenerateOrderId(@Body request: CheckoutRequest): GenerateOrderIdResponse

    @FormUrlEncoded
    @POST("/pos/printInvoicesUnPaid")
    suspend fun requestPaymentNotification(@FieldMap body: HashMap<String, String>): PaymentNotificationResponse

}