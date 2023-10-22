package com.shady.boyot.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shady.domain.entity.responses.BaseResponse
import com.shady.domain.entity.responses.LoginResponse
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

    public fun clear() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

}