package com.boyot.collector.ui.receipt_details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boyot.collector.R
import com.boyot.collector.base.BaseFragment
import com.boyot.collector.databinding.FragmentReceiptDetailsBinding
import com.boyot.collector.domain.entity.beans.ReceiptBean
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
        binding.ivHeaderLogo.setOnClickListener {
            navigate(R.id.global_action_back_to_users_search);
        }
        binding.tvReturnToAnotherOperation.setOnClickListener {
            popBackStack()
        }
        binding.btnPrint.setOnClickListener {
            onPrintClick()
        }
        initArguments()

    }

    private fun initArguments() {
        val args = ReceiptDetailsFragmentArgs.fromBundle(requireArguments())
        receipt = args.receipt
        setData()
    }

    private fun onPrintClick() {

    }


    private fun setData() {
        binding.tvTitle.text = Html.fromHtml(
            requireContext().getString(
                R.string.your_transaction_was_completed_successfully_in,
                receipt.invoice_number,
                receipt.invoice_date
            )
        )
        binding.tvName.text = receipt.collection_date
        binding.tvReceiptId.text = Html.fromHtml(
            requireContext().getString(R.string.operation_number_, receipt.invoice_number)
        )
        binding.tvAddress.text = requireContext().getString(R.string.address_, receipt.getFullAddress())
        binding.tvServiceType.text = receipt.service_type
        binding.tvClientName.text = receipt.client_name
        binding.tvClientPhone.text = receipt.client_phone
        binding.tvBuildingNumber.text = receipt.address
        binding.tvUnitNumber.text = receipt.unit_number
        binding.tvRegion.text = receipt.region
        binding.tvPaymentMethod.text = receipt.payment_method
        binding.tvTotalCost.text = receipt.total_amount

        binding.tvContractType.text = receipt.service_type
        binding.tvInvoiceNumber.text = receipt.invoice_number

    }


}