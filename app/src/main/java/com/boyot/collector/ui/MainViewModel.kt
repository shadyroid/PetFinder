package com.boyot.collector.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.boyot.collector.data.preferenceses.PreferencesHelper
import com.boyot.collector.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel() {

    fun clearUserData() {
        preferencesHelper.clear()
    }
    val onCameraTakePictureMutableStateFlow = MutableStateFlow<String?>(null)


}