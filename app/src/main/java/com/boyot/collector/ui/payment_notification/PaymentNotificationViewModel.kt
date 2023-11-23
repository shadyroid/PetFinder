package com.boyot.collector.ui.payment_notification

import androidx.lifecycle.viewModelScope
import com.boyot.collector.base.BaseViewModel
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.domain.entity.responses.PaymentNotificationResponse
import com.boyot.collector.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentNotificationViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _paymentNotificationResponseMutableStateFlow = MutableStateFlow<PaymentNotificationResponse?>(null)

    val paymentNotificationResponseMutableStateFlow: StateFlow<PaymentNotificationResponse?> = _paymentNotificationResponseMutableStateFlow


    fun requestPaymentNotification(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestPaymentNotification(body)
                if (ResponseHandler.isSuccess(response)) {
                    _paymentNotificationResponseMutableStateFlow.value = response
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