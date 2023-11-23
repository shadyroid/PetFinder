package com.boyot.collector.ui.receipts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.boyot.collector.R
import com.boyot.collector.base.BaseFragment
import com.boyot.collector.classes.adapters.ReceiptsAdapter
import com.boyot.collector.databinding.FragmentReceiptsBinding
import com.boyot.collector.domain.entity.beans.ReceiptBean
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.ReceiptsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReceiptsFragment : BaseFragment(), ReceiptsAdapter.Listener {
    private lateinit var userId: String
    private lateinit var userName: String
    private var _binding: FragmentReceiptsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReceiptsViewModel by viewModels()


    @Inject
    lateinit var receiptsAdapter: ReceiptsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentReceiptsBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            resetState()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        binding.ivHeaderLogo.setOnClickListener {
            navigate(R.id.global_action_back_to_users_search);
        }
        initReceiptsAdapter()
        initArguments()
        initObserves()
        requestReceipts()
        binding.toolbar.title = getString(R.string.receipts_, userName)
    }

    private fun initArguments() {
        val args = ReceiptsFragmentArgs.fromBundle(requireArguments())
        userId = args.userId
        userName = args.userName

    }

    private fun initReceiptsAdapter() {
        receiptsAdapter.listener = this
        binding.rvReceipts.adapter = receiptsAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }

        lifecycleScope.launch {
            viewModel.receiptsResponseMutableStateFlow.collect {
                if (it != null) onReceiptsResponse(it)
            }
        }

    }


    fun requestReceipts() {
        val map = HashMap<String, String>()
        map["actor_id"] = userId;
        viewModel.requestReceipts(map)
    }


    private fun onReceiptsResponse(response: ReceiptsResponse) {
        binding.shimmer.visibility = View.GONE
        binding.rvReceipts.visibility = View.VISIBLE
        response.data?.let {
            binding.tvNoResults.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            if (it.size == 0)
                response.message?.let { appToast.showMessage(it) }

            receiptsAdapter.addData(it)
        }

    }

    override fun onLoading(isLoading: Boolean) {
        super.onLoading(isLoading)
        if (isLoading) {
            binding.shimmer.visibility = View.VISIBLE
            binding.rvReceipts.visibility = View.GONE
            binding.tvNoResults.visibility = View.GONE
        }

    }

    override fun onApiError(response: BaseResponse?) {
        super.onApiError(response)
        binding.shimmer.visibility = View.GONE
        binding.rvReceipts.visibility = View.GONE
        binding.tvNoResults.visibility = View.VISIBLE
    }

    override fun onReceiptClick(receipt: ReceiptBean) {
        navigate(ReceiptsFragmentDirections.actionNavReceiptsToNavReceiptDetails(receipt))

    }

    private fun resetState() {
        viewModel.clear()
        receiptsAdapter.clear()
        requestReceipts()
    }
}