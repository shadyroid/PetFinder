package com.shady.boyot.ui.checkout

import androidx.lifecycle.viewModelScope
import com.shady.boyot.base.BaseViewModel
import com.shady.boyot.classes.utils.ResponseHandler
import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.CheckoutResponse
import com.shady.domain.entity.responses.GenerateOrderIdResponse
import com.shady.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _cashCheckoutResponseMutableStateFlow = MutableStateFlow<CheckoutResponse?>(null)

    val cashCheckoutResponseMutableStateFlow: StateFlow<CheckoutResponse?> = _cashCheckoutResponseMutableStateFlow

    private val _generateOrderIdResponseMutableStateFlow = MutableStateFlow<GenerateOrderIdResponse?>(null)

    val generateOrderIdResponseMutableStateFlow: StateFlow<GenerateOrderIdResponse?> = _generateOrderIdResponseMutableStateFlow

    private val _visaCheckoutResponseMutableStateFlow = MutableStateFlow<CheckoutResponse?>(null)

    val visaCheckoutResponseMutableStateFlow: StateFlow<CheckoutResponse?> = _visaCheckoutResponseMutableStateFlow



    fun requestCashCheckout(request: CheckoutRequest) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestCashCheckout(request)
                if (ResponseHandler.isSuccess(response)) {
                    _cashCheckoutResponseMutableStateFlow.value = response
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
    fun requestGenerateOrderId(request: CheckoutRequest) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestGenerateOrderId(request)
                if (ResponseHandler.isSuccess(response)) {
                    _generateOrderIdResponseMutableStateFlow.value = response
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
    fun requestVisaCheckout(request: CheckoutRequest) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestVisaCheckout(request)
                if (ResponseHandler.isSuccess(response)) {
                    _visaCheckoutResponseMutableStateFlow.value = response
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