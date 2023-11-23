package com.boyot.collector.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.boyot.collector.R
import com.boyot.collector.base.BaseFragment
import com.boyot.collector.classes.adapters.UsersAdapter
import com.boyot.collector.databinding.FragmentUsersBinding
import com.boyot.collector.domain.entity.beans.UserBean
import com.boyot.collector.domain.entity.responses.BaseResponse
import com.boyot.collector.domain.entity.responses.UsersResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : BaseFragment(), UsersAdapter.Listener {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersViewModel by viewModels()

    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var userAddress: String
    private var clientCode: String? = null
    private var clientName: String? = null
    private var contractNumber: String? = null
    private var buildingId: String? = null
    private var unitId: String? = null

    @Inject
    lateinit var usersAdapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentUsersBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    override fun onLoading(isLoading: Boolean) {
        super.onLoading(isLoading)
        if (isLoading) {
            binding.shimmer.visibility = VISIBLE
            binding.rvUsers.visibility = GONE
            binding.btnContinue.visibility = GONE
            binding.tvNoResults.visibility = GONE
        }
    }

    override fun onApiError(response: BaseResponse?) {
        super.onApiError(response)
        binding.shimmer.visibility = GONE
        binding.rvUsers.visibility = GONE
        binding.btnContinue.visibility = GONE
        binding.tvNoResults.visibility = VISIBLE
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
        binding.btnContinue.setOnClickListener {
            navigate(
                UsersFragmentDirections.actionNavUsersToNavOptions(
                    userId,
                    userName,
                    userAddress
                )
            )
        }
        initUsersAdapter()
        initArguments()
        initObserves()

        requestUsers()

    }

    private fun initArguments() {
        val args = UsersFragmentArgs.fromBundle(requireArguments())
        clientCode = args.clientCode
        clientName = args.clientName
        contractNumber = args.contractNumber
        buildingId = args.buildingId
        unitId = args.unitId

    }


    private fun initUsersAdapter() {
        usersAdapter.listener = this
        binding.rvUsers.adapter = usersAdapter
    }

    private fun initObserves() {
        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }

        lifecycleScope.launch {
            viewModel.usersResponseMutableStateFlow.collect {
                if (it != null) onUsersResponse(it)
            }
        }

    }


    fun requestUsers() {

        val map = HashMap<String, String>()
        clientCode?.let { map["client_code"] = it }
        clientName?.let { map["client_name"] = it }
        contractNumber?.let { map["contract_number"] = it }
        buildingId?.let { map["building_id"] = it }
        unitId?.let { map["unit_id"] = it }
        viewModel.requestUsers(map)
    }


    private fun onUsersResponse(response: UsersResponse) {
        binding.shimmer.visibility = GONE
        binding.rvUsers.visibility = VISIBLE
        binding.btnContinue.visibility = VISIBLE
        response.data?.let {
            binding.tvNoResults.visibility = if (it.isEmpty()) VISIBLE else GONE
            usersAdapter.addData(it)
            onUserClick(it[0])
        }

    }

    override fun onUserClick(user: UserBean) {
        userId = user.actor_id ?: "0";
        userName = user.actor_name ?: "";
        userAddress = user.building_name ?: "";
    }

    private fun resetState() {
        viewModel.clear()
        usersAdapter.clear()
        requestUsers()
    }
}