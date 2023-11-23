package com.boyot.collector.ui.edit_user

import androidx.lifecycle.viewModelScope
import com.boyot.collector.base.BaseViewModel
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.data.preferenceses.PreferencesHelper
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.UserDetailsResponse
import com.boyot.collector.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel() {

    val editUserResponseMutableStateFlow = MutableStateFlow<BaseResponse?>(null)
    val userDetailsResponseMutableStateFlow = MutableStateFlow<UserDetailsResponse?>(null)

    fun requestEditUser(userId:String ,body: HashMap<String, RequestBody>, part: MultipartBody.Part?) {
        loadingMutableStateFlow.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = usersUseCase.requestEditUser(userId,body,part)
                if (ResponseHandler.isSuccess(response)) {
                    editUserResponseMutableStateFlow.value = response
                } else {
                    apiErrorMutableStateFlow.value = response
                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                loadingMutableStateFlow.value = false
            }
        }

    }

    fun requestUserDetails(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = usersUseCase.requestUserDetails(body)
                if (ResponseHandler.isSuccess(response)) {
                    userDetailsResponseMutableStateFlow.value = response
                } else {
                    apiErrorMutableStateFlow.value = response
                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                loadingMutableStateFlow.value = false
            }
        }

    }


}