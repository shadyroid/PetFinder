package com.boyot.collector.ui.payment_notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fawry.pos.retailer.connect.FawryConnect
import com.fawry.pos.retailer.connect.model.payment.PaymentOptionType
import com.fawry.pos.retailer.modelBuilder.sale.CardSale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.boyot.collector.R
import com.boyot.collector.base.BaseFragment
import com.boyot.collector.classes.adapters.UnitsAdapter
import com.boyot.collector.classes.others.CONSTANTS.BTC
import com.boyot.collector.databinding.FragmentPaymentNotificationBinding
import com.boyot.collector.ui.MainActivity
import com.boyot.collector.domain.entity.responses.PaymentNotificationResponse
import com.boyot.collector.domain.entity.responses.GenerateOrderIdResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class PaymentNotificationFragment : BaseFragment() {
    private var orderId: String? = ""
    private lateinit var userId: String
    private var _binding: FragmentPaymentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PaymentNotificationViewModel by viewModels()


    @Inject
    lateinit var unitsAdapter: UnitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentPaymentNotificationBinding.inflate(inflater, container, false)
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
        initUnitsAdapter()
        initArguments()
        initObserves()
        requestPaymentNotification()
    }

    private fun initArguments() {
        val args = PaymentNotificationFragmentArgs.fromBundle(requireArguments())
        userId = args.userId
    }


    private fun initUnitsAdapter() {
        binding.rvUnits.adapter = unitsAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.paymentNotificationResponseMutableStateFlow.collect {
                if (it != null) onPaymentNotificationResponse(it)
            }
        }

    }

    fun requestPaymentNotification() {
        val map = HashMap<String, String>()
        map["actor_id"] = userId;
        viewModel.requestPaymentNotification(map)
    }




    private fun onPaymentNotificationResponse(response: PaymentNotificationResponse) {
        response.data?.let {
            binding.tvClientName.text = it.client_name
            binding.tvClientPhone.text = it.client_phone
            binding.tvInvoicesCount.text = it.total_invoices_not_paid
            binding.tvInvoicesTotalCost.text = it.amount_of_invoices
            it.unit?.let {
                unitsAdapter.addData(it)
            }
        }
    }




}