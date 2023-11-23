package com.boyot.collector.ui.receipts

import androidx.lifecycle.viewModelScope
import com.boyot.collector.base.BaseViewModel
import com.boyot.collector.classes.utils.ResponseHandler
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import com.boyot.collector.domain.usecase.InvoicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptsViewModel @Inject constructor(
    private val invoicesUseCase: InvoicesUseCase,
) : BaseViewModel() {

    private val _receiptsResponseMutableStateFlow = MutableStateFlow<ReceiptsResponse?>(null)

    val receiptsResponseMutableStateFlow: StateFlow<ReceiptsResponse?> = _receiptsResponseMutableStateFlow



    fun requestReceipts(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = invoicesUseCase.requestReceipts(body)
                if (ResponseHandler.isSuccess(response)) {
                    _receiptsResponseMutableStateFlow.value = response
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