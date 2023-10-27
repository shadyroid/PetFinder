package com.shady.boyot.ui.receipt_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.databinding.FragmentReceiptDetailsBinding
import com.shady.domain.entity.beans.ReceiptBean
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiptDetailsFragment : BaseFragment() {
    private var _binding: FragmentReceiptDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var receipt: ReceiptBean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentReceiptDetailsBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        initObserves()
        initArguments()

    }

    private fun initArguments() {
        val args = ReceiptDetailsFragmentArgs.fromBundle(requireArguments())
        receipt = args.receipt

    }


    private fun initObserves() {


    }


}