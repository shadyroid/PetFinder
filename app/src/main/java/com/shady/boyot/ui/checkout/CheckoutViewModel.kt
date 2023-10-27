package com.shady.boyot.ui.checkout

import androidx.lifecycle.viewModelScope
import com.shady.boyot.base.BaseViewModel
import com.shady.boyot.classes.utils.ResponseHandler
import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _checkoutResponseMutableStateFlow = MutableStateFlow<CheckoutResponse?>(null)

    val checkoutResponseMutableStateFlow: StateFlow<CheckoutResponse?> = _checkoutResponseMutableStateFlow



    fun requestCheckout(request: CheckoutRequest) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestCheckout(request)
                if (ResponseHandler.isSuccess(response)) {
                    _checkoutResponseMutableStateFlow.value = response
                } else {
                    apiErrorMutableStateFlow.value = response
                }
            } catch (e: Exception) {
                onError(e)
            } finally {
                loadingMutableStateFlow.value = false
            }
        })

    }
}