package com.shady.domain.repo

import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UserDetailsResponse
import com.shady.domain.entity.responses.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UsersRepo {
    suspend fun requestUsers(params: HashMap<String, String>): UsersResponse
    suspend fun requestEditUser(userId:String ,params: HashMap<String, RequestBody>, part: MultipartBody.Part?): BaseResponse
    suspend fun requestUserDetails(params: HashMap<String, String>): UserDetailsResponse
}