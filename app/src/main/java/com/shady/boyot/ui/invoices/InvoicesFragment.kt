package com.shady.boyot.ui.invoices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shady.boyot.classes.adapters.InvoicesAdapter
import com.shady.boyot.classes.utils.EndlessRecyclerViewScrollListener
import com.shady.boyot.databinding.FragmentInvoicesBinding
import com.shady.boyot.ui.users.UsersFragmentDirections
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.domain.entity.responses.InvoicesResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InvoicesFragment : Fragment(), InvoicesAdapter.Listener {
    private var _binding: FragmentInvoicesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InvoicesViewModel by viewModels()

    private var currentPage = 1

    @Inject
    lateinit var invoicesAdapter: InvoicesAdapter
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener


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
        binding.btnPayNow.setOnClickListener{
            binding.root.findNavController()
                .navigate(InvoicesFragmentDirections.actionNavInvoicesToNavPaymentMethods())
        }
        initInvoicesAdapter()
        initObserves()
        requestInvoices()

    }


    private fun initInvoicesAdapter() {
        invoicesAdapter.listener = this
        binding.rvInvoices.adapter = invoicesAdapter
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener() {
                override fun onLoadMore(page: Int) {
                    currentPage = page
                    requestInvoices()
                }
            }
        binding.rvInvoices.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            viewModel.invoicesResponseMutableStateFlow.collect {
                if (it != null) onInvoicesResponse(it)
            }
        }

    }


    fun requestInvoices() {
        val map = HashMap<String, String>()
        map["page"] = currentPage.toString()
        viewModel.requestInvoices(map)
    }


    private fun onInvoicesResponse(response: InvoicesResponse) {
        if (currentPage == 1) {
            binding.shimmer.visibility = View.GONE
            binding.rvInvoices.visibility = View.VISIBLE
        }
        response.data?.let {
            invoicesAdapter.setFinishedLoading(it.size < 5)
            invoicesAdapter.addData(it)
        }

    }

    override fun onInvoiceClick(invoice: InvoiceBean) {

    }

    private fun resetState() {
        viewModel.clear()
        invoicesAdapter.clear()
        endlessRecyclerViewScrollListener.resetState()
        binding.shimmer.visibility = View.VISIBLE
        binding.rvInvoices.visibility = View.GONE
        currentPage = 1
        requestInvoices()
    }
}