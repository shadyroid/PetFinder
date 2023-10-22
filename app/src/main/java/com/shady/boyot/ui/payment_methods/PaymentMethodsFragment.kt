package com.shady.boyot.ui.payment_methods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.shady.boyot.databinding.FragmentPaymentMethodsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMethodsFragment : Fragment() {
    private var _binding: FragmentPaymentMethodsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentPaymentMethodsBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {

        binding.btnContinue.setOnClickListener {
            binding.root.findNavController()
                .navigate(PaymentMethodsFragmentDirections.actionNavPaymentMethodsToNavInvoiceDetails())
        }
        binding.rbVisa.setOnClickListener {
            binding.rbVisa.isChecked = true
            binding.rbCash.isChecked = false
        }
        binding.cvVisa.setOnClickListener {
            binding.rbVisa.isChecked = true
            binding.rbCash.isChecked = false
        }
        binding.rbCash.setOnClickListener {
            binding.rbVisa.isChecked = false
            binding.rbCash.isChecked = true
        }
        binding.cvCash.setOnClickListener {
            binding.rbVisa.isChecked = false
            binding.rbCash.isChecked = true
        }
        initObserves()

    }


    private fun initObserves() {


    }


}