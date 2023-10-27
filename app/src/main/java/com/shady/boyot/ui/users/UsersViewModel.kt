package com.shady.boyot.ui.users

import androidx.lifecycle.viewModelScope
import com.shady.boyot.base.BaseViewModel
import com.shady.boyot.classes.utils.ResponseHandler
import com.shady.domain.entity.responses.UsersResponse
import com.shady.domain.usecase.InvoicesUseCase
import com.shady.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
) : BaseViewModel() {

    private val _usersResponseMutableStateFlow = MutableStateFlow<UsersResponse?>(null)

    val usersResponseMutableStateFlow: StateFlow<UsersResponse?> = _usersResponseMutableStateFlow


    fun requestUsers(body: HashMap<String, String>) {
        loadingMutableStateFlow.value = true
        jobs.add(viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = usersUseCase.requestUsers(body)
                if (ResponseHandler.isSuccess(response)) {
                    _usersResponseMutableStateFlow.value = response
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

    override fun clear() {
        super.clear()

    }
}