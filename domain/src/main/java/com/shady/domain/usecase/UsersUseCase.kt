package com.shady.domain.usecase

import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.BuildingsResponse
import com.shady.domain.entity.responses.ReceiptsResponse
import com.shady.domain.entity.responses.UserDetailsResponse
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.repo.UsersRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UsersUseCase(private val usersRepo: UsersRepo) {
    suspend fun requestUsers(params: HashMap<String, String>): UsersResponse =
        usersRepo.requestUsers(params)
    suspend fun requestEditUser(userId:String ,params: HashMap<String, RequestBody>, part: MultipartBody.Part?): BaseResponse =
        usersRepo.requestEditUser(userId,params,part)
    suspend fun requestUserDetails(params: HashMap<String, String>): UserDetailsResponse =
        usersRepo.requestUserDetails(params)




}