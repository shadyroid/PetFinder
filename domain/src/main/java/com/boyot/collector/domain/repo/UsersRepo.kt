package com.boyot.collector.domain.repo

import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.BuildingsResponse
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import com.boyot.collector.domain.entity.responses.UserDetailsResponse
import com.boyot.collector.domain.entity.responses.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UsersRepo {
    suspend fun requestUsers(params: HashMap<String, String>): UsersResponse
    suspend fun requestEditUser(userId:String ,params: HashMap<String, RequestBody>, part: MultipartBody.Part?): BaseResponse
    suspend fun requestUserDetails(params: HashMap<String, String>): UserDetailsResponse
}