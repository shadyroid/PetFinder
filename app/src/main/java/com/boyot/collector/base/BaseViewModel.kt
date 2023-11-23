package com.boyot.collector.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    val apiErrorMutableStateFlow = MutableStateFlow<BaseResponse?>(null)
    val loadingMutableStateFlow = MutableStateFlow(false)
    protected val jobs = mutableListOf<Job>()

    protected fun onError(e: Throwable) {
        Log.d("error ", "error : $e")
    }

   open public fun clear() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

}