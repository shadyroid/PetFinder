package com.shady.boyot.ui.receipts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shady.boyot.R
import com.shady.boyot.classes.adapters.ReceiptsAdapter
import com.shady.boyot.databinding.FragmentReceiptsBinding
import com.shady.domain.entity.beans.ReceiptBean
import com.shady.domain.entity.responses.ReceiptsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReceiptsFragment : Fragment(), ReceiptsAdapter.Listener {
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
            binding.root.findNavController().popBackStack()
        }
        initReceiptsAdapter()
        initArguments()
        initObserves()
        requestReceipts()
        binding.toolbar.title = "$userName ${getString(R.string.receipts)}"

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
            receiptsAdapter.setFinishedLoading(it.size < 5)
            receiptsAdapter.addData(it)
        }

    }

    override fun onReceiptClick(receipt: ReceiptBean) {

    }

    private fun resetState() {
        viewModel.clear()
        receiptsAdapter.clear()
        binding.shimmer.visibility = View.VISIBLE
        binding.rvReceipts.visibility = View.GONE
        requestReceipts()
    }
}