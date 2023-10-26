package com.shady.boyot.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.utils.Validator
import com.shady.boyot.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var validator: Validator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentLoginBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        initArguments()
        initObserves()

        binding.btnLogin.setOnClickListener { onLoginClick() }

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isLoggedIn) {
            navigate(LoginFragmentDirections.actionReplaceNavLoginWithNavUsersSearch())
        }
    }

    private fun initArguments() {
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.loginResponseMutableStateFlow.collect { if (it != null) onLoginResponse() }
        }
    }

    private fun onLoginClick() {
        if (!isValid()) return
        val map = HashMap<String, String>()
        map["user_name"] = binding.etUserName.text.toString()
        map["password"] = binding.etPassword.text.toString()
        viewModel.requestLogin(map)
    }

    private fun onLoginResponse() {
        navigate(LoginFragmentDirections.actionReplaceNavLoginWithNavUsersSearch())
    }

    private fun isValid(): Boolean =
        validator.isNotEmpty(
            binding.etUserName,
            R.string.please_enter_your_email_or_phone_number
        ) &&
                validator.isNotEmpty(binding.etPassword, R.string.please_enter_your_password) &&
                validator.isMoreThan(
                    binding.etPassword,
                    5,
                    R.string.password_should_be_more_than_6_characters
                )
}