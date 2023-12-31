package com.boyot.collector.ui.invoices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.boyot.collector.R
import com.boyot.collector.base.BaseFragment
import com.boyot.collector.classes.adapters.InvoicesAdapter
import com.boyot.collector.databinding.FragmentInvoicesBinding
import com.boyot.collector.domain.entity.beans.InvoiceBean
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.InvoicesResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InvoicesFragment : BaseFragment(), InvoicesAdapter.Listener {
    private lateinit var userId: String
    private lateinit var userName: String
    private var _binding: FragmentInvoicesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InvoicesViewModel by viewModels()


    @Inject
    lateinit var invoicesAdapter: InvoicesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentInvoicesBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            resetState()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.btnPayNow.setOnClickListener {
            if (invoicesAdapter.getSelectedInvoicesCount()==0){
                appToast.showMessage(getString(R.string.you_should_select_at_least_one_invoice))
                return@setOnClickListener
            }
            navigate(InvoicesFragmentDirections.actionNavInvoicesToNavPaymentMethods( Gson().toJson(invoicesAdapter.getSelectedInvoices())))
        }
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.ivHeaderLogo.setOnClickListener {
            navigate(R.id.global_action_back_to_users_search);
        }
        initInvoicesAdapter()
        initArguments()
        initObserves()
        requestInvoices()
        binding.toolbar.title = getString(R.string.invoices_,  userName)

    }

    private fun initArguments() {
        val args = InvoicesFragmentArgs.fromBundle(requireArguments())
        userId = args.userId
        userName = args.userName

    }

    private fun initInvoicesAdapter() {
        invoicesAdapter.listener = this
        binding.rvInvoices.adapter = invoicesAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.invoicesResponseMutableStateFlow.collect {
                if (it != null) onInvoicesResponse(it)
            }
        }

    }

    override fun onLoading(isLoading: Boolean) {
        super.onLoading(isLoading)
        if (isLoading) {
            binding.shimmer.visibility = View.VISIBLE
            binding.rvInvoices.visibility = View.GONE
            binding.btnPayNow.visibility = View.GONE
            binding.tvNoResults.visibility = View.GONE
        }
    }
    override fun onApiError(response: BaseResponse?) {
        super.onApiError(response)
        binding.shimmer.visibility = View.GONE
        binding.rvInvoices.visibility = View.GONE
        binding.btnPayNow.visibility = View.GONE
        binding.tvNoResults.visibility = View.VISIBLE
    }
    fun requestInvoices() {
        val map = HashMap<String, String>()
        map["actor_id"] = userId;
        viewModel.requestInvoices(map)
    }


    private fun onInvoicesResponse(response: InvoicesResponse) {
        binding.shimmer.visibility = View.GONE
        binding.rvInvoices.visibility = View.VISIBLE
        binding.btnPayNow.visibility = View.VISIBLE
        response.data?.let {
            binding.tvNoResults.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            invoicesAdapter.addData(it)
        }

    }

    override fun onInvoiceClick(invoice: InvoiceBean) {

    }

    private fun resetState() {
        viewModel.clear()
        invoicesAdapter.clear()
        requestInvoices()
    }
}