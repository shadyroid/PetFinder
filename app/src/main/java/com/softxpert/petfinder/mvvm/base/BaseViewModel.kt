package com.softxpert.petfinder.mvvm.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.softxpert.petfinder.classes.rest.Repository
import kotlinx.coroutines.Job


open class BaseViewModel : ViewModel() {
    var repository: Repository = Repository()
    val jobs = mutableListOf<Job>()
    open fun clear() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    fun onError(e: Throwable) {
        Log.d("crash_tryObserver", "error : $e")
    }
}