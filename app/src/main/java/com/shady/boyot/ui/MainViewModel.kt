package com.shady.boyot.ui

import androidx.lifecycle.ViewModel
import com.shady.data.preferenceses.PreferencesHelper
import com.shady.boyot.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel() {

    fun clearUserData() {
        preferencesHelper.clear()
    }


}