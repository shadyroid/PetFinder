package com.shady.boyot.ui.invoices

import androidx.lifecycle.viewModelScope
import com.shady.boyot.base.BaseViewModel
import com.shady.boyot.classes.utils.ResponseHandler
import com.shady.domain.entity.responses.InvoicesResponse
import com.shady.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _invoicesResponseMutableStateFlow = MutableStateFlow<InvoicesResponse?>(null)

    val invoicesResponseMutableStateFlow: StateFlow<InvoicesResponse?> = _invoicesResponseMutableStateFlow



    fun requestInvoices(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestInvoices(body)
                if (ResponseHandler.isSuccess(response)) {
                    _invoicesResponseMutableStateFlow.value = response
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