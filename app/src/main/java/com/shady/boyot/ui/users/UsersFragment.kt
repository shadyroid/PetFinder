package com.shady.boyot.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.adapters.UsersAdapter
import com.shady.boyot.databinding.FragmentUsersBinding
import com.shady.domain.entity.beans.UserBean
import com.shady.domain.entity.responses.UsersResponse
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
            navigate(UsersFragmentDirections.actionNavUsersToNavOptions(userId, userName,userAddress))
        }
        initUsersAdapter()
        initArguments()
        initObserves()
        binding.shimmer.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
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
        binding.shimmer.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
        response.data?.let {
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
        binding.shimmer.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
        requestUsers()
    }
}