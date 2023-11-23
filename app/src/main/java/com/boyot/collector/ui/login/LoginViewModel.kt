package com.boyot.collector.ui.login

import androidx.lifecycle.viewModelScope
import com.boyot.collector.base.BaseViewModel
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.data.preferenceses.PreferencesHelper
import com.boyot.collector.domain.entity.responses.LoginResponse
import com.boyot.collector.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel() {

    val isLoggedIn: Boolean = preferencesHelper.isLoggedIn()
    val loginResponseMutableStateFlow = MutableStateFlow<LoginResponse?>(null)

    fun requestLogin(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authUseCase.requestLogin(body)
                if (ResponseHandler.isSuccess(response)) {
                    preferencesHelper.putAuthToken("${response.data?.token}")
                    loginResponseMutableStateFlow.value = response
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