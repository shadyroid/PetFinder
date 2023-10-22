package com.shady.boyot.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.databinding.FragmentOptionsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OptionsFragment : BaseFragment() {
    private lateinit var binding: FragmentOptionsBinding


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

        binding.btnInvoices.setOnClickListener {
            navigate(OptionsFragmentDirections.actionNavOptionsToNavInvoices())
        }
        binding.btnReceipts.setOnClickListener {
//            navigate(OptionsFragmentDirections.actionNavOptionsToNavReceipts())
        }
        binding.btnPaymentNotification.setOnClickListener {
//            navigate(OptionsFragmentDirections.actionNavOptionsToNavPaymentNotification())
        }
        binding.btnDataUpdate.setOnClickListener {
//            navigate(OptionsFragmentDirections.actionNavOptionsToNavEditUser())
        }

    }


    private fun initArguments() {
    }

    private fun initObserves() {

    }


    private fun onOptionsResponse() {
    }


}