package com.shady.boyot.ui.invoices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.adapters.InvoicesAdapter
import com.shady.boyot.databinding.FragmentInvoicesBinding
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.responses.InvoicesResponse
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
        binding.shimmer.visibility = View.VISIBLE
        binding.rvInvoices.visibility = View.GONE
        requestInvoices()
        binding.toolbar.title = "${getString(R.string.invoices)} $userName"
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


    fun requestInvoices() {
        val map = HashMap<String, String>()
        map["actor_id"] = userId;
        viewModel.requestInvoices(map)
    }


    private fun onInvoicesResponse(response: InvoicesResponse) {
        binding.shimmer.visibility = View.GONE
        binding.rvInvoices.visibility = View.VISIBLE
        response.data?.let {
            invoicesAdapter.addData(it)
        }

    }

    override fun onInvoiceClick(invoice: InvoiceBean) {

    }

    private fun resetState() {
        viewModel.clear()
        invoicesAdapter.clear()
        binding.shimmer.visibility = View.VISIBLE
        binding.rvInvoices.visibility = View.GONE
        requestInvoices()
    }
}