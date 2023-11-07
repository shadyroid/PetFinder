package com.shady.boyot.ui.checkout

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
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.adapters.CheckoutsAdapter
import com.shady.boyot.classes.others.CONSTANTS.BTC
import com.shady.boyot.databinding.FragmentCheckoutBinding
import com.shady.boyot.ui.MainActivity
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.requests.CheckoutRequest
import com.shady.domain.entity.responses.CheckoutResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CheckoutFragment : BaseFragment() {
    private val orderId: String? = "ddfdsfdsfdsfdsfdsfdsfdsfdsf"
    private var tax: Double = 0.0
    private var serviceExpenses: Double = 0.0
    private var totalCost: Double = 0.0
    private var cost: Double = 0.0
    private var paymentMethodId: Int = 0
    private lateinit var invoices: List<InvoiceBean>
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModels()


    @Inject
    lateinit var checkoutsAdapter: CheckoutsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.btnCheckout.setOnClickListener {
            onCheckoutClick()
        }
        initCheckoutsAdapter()
        initArguments()
        initObserves()
    }

    private fun initArguments() {
        val args = CheckoutFragmentArgs.fromBundle(requireArguments())
        invoices = Gson().fromJson(args.invoices, object : TypeToken<List<InvoiceBean>>() {}.type)
        paymentMethodId = args.paymentMethodId

        setData()

    }

    private fun setData() {
        checkoutsAdapter.addData(invoices)

        cost = checkoutsAdapter.calculateTotalCost()
        totalCost = cost + serviceExpenses + tax



        binding.tvPaymentMethod.text = getString(
            if (paymentMethodId == 1) R.string.cash else R.string.visa
        )
        binding.tvCost.text = getString(R.string._egp, String.format("%.2f", cost))
        binding.tvServiceExpenses.text =
            getString(R.string._egp, String.format("%.2f", serviceExpenses))
        binding.tvTax.text = getString(R.string._egp, String.format("%.2f", tax))
        binding.tvTotalCost.text = getString(R.string._egp, String.format("%.2f", totalCost))
    }

    private fun initCheckoutsAdapter() {
        binding.rvCheckouts.adapter = checkoutsAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.checkoutResponseMutableStateFlow.collect {
                if (it != null) onCheckoutResponse(it)
            }
        }

    }


    fun requestCheckout() {
        val invoiceIds: MutableList<String> = mutableListOf()
        for (invoice in invoices) {
            invoice.invoice_id?.let { invoiceIds.add(it) }
        }
        viewModel.requestCheckout(CheckoutRequest(invoiceIds))
    }


    private fun onCheckoutResponse(response: CheckoutResponse) {
        response.message?.let { appToast.showMessage(it) }
        response.data?.let {
            navigate(
                CheckoutFragmentDirections.actionNavCheckoutToNavReceiptDetails(
                    it
                )
            )
        }
    }

    private fun onCheckoutClick() {
        if (paymentMethodId == 1)
            requestCheckout()
        else
            (activity as MainActivity).fawryConnect
                ?.requestSale<CardSale.Builder>(PaymentOptionType.CARD)
                ?.setAmount(totalCost)
                ?.setCurrency("EGP")
                ?.setPrintReceipt(true)
                ?.setDisplayInvoice(false)
                ?.setOrderID(orderId)
                ?.send(
                    BTC,
                    FawryConnect.OnTransactionCallBack(
                        onTransactionRequestSuccess = {
                            requestCheckout()
                        },
                        onTransactionRequestFailure = { payment, throwable ->
                            Toast.makeText(requireContext(), "لم يتم الدف بسبب\n"+ throwable?.message, Toast.LENGTH_SHORT).show()
                        }
                    )
                )

    }


}