package com.shady.boyot.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.databinding.FragmentOptionsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OptionsFragment : BaseFragment() {
    private lateinit var binding: FragmentOptionsBinding

    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var userAddress: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentOptionsBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        initArguments()
        initObserves()
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        binding.ivHeaderLogo.setOnClickListener {
            navigate(R.id.global_action_back_to_users_search);
        }
        binding.btnInvoices.setOnClickListener {
            navigate(OptionsFragmentDirections.actionNavOptionsToNavInvoices(userId, userName))
        }
        binding.btnReceipts.setOnClickListener {
            navigate(OptionsFragmentDirections.actionNavOptionsToNavReceipts(userId, userName))
        }
        binding.btnPaymentNotification.setOnClickListener {
            navigate(OptionsFragmentDirections.actionNavOptionsToNavPaymentNotification(userId))
        }
        binding.btnDataUpdate.setOnClickListener {
            navigate(OptionsFragmentDirections.actionNavOptionsToNavEditUser(userId))
        }

        binding.tvName.setText(userName)
        binding.tvId.setText(userId)
        binding.tvAddress.setText(userAddress)

    }


    private fun initArguments() {
        val args = OptionsFragmentArgs.fromBundle(requireArguments())
        userId = args.userId
        userName = args.userName
        userAddress = args.userAddress
    }

    private fun initObserves() {

    }


    private fun onOptionsResponse() {
    }


}