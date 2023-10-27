package com.shady.data.repo

import com.shady.data.remote.ApiService
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.UserDetailsResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.UsersRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UsersRepoImpl(private val apiService: ApiService) : UsersRepo {
    override suspend fun requestUsers(body: HashMap<String, String>): UsersResponse =
        apiService.requestUsers(body)

    override suspend fun requestEditUser(userId:String ,body: HashMap<String, RequestBody>, part: MultipartBody.Part?): BaseResponse =
        apiService.requestEditUser(userId, body,part)

    override suspend fun requestUserDetails(body: HashMap<String, String>): UserDetailsResponse =
        apiService.requestUserDetails(body["user_id"]!!)


}