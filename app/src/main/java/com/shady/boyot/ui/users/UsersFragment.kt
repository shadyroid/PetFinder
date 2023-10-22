package com.shady.boyot.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shady.boyot.classes.adapters.UsersAdapter
import com.shady.boyot.classes.utils.EndlessRecyclerViewScrollListener
import com.shady.boyot.databinding.FragmentUsersBinding
import com.shady.domain.entity.beans.UserBean
import com.shady.domain.entity.responses.UsersResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(), UsersAdapter.Listener {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersViewModel by viewModels()

    private var currentPage = 1

    @Inject
    lateinit var usersAdapter: UsersAdapter
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener


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
        binding.btnContinue.setOnClickListener{
            binding.root.findNavController()
                .navigate(UsersFragmentDirections.actionNavUsersToNavOptions())
        }
        initUsersAdapter()
        initObserves()
        requestUsers()

    }


    private fun initUsersAdapter() {
        usersAdapter.listener = this
        binding.rvUsers.adapter = usersAdapter
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener() {
                override fun onLoadMore(page: Int) {
                    currentPage = page
                    requestUsers()
                }
            }
        binding.rvUsers.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun initObserves() {
        lifecycleScope.launch {
            viewModel.usersResponseMutableStateFlow.collect {
                if (it != null) onUsersResponse(it)
            }
        }

    }


    fun requestUsers() {
        val map = HashMap<String, String>()
        map["page"] = currentPage.toString()
        viewModel.requestUsers(map)
    }


    private fun onUsersResponse(response: UsersResponse) {
        if (currentPage == 1) {
            binding.shimmer.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
        response.data?.let {
            usersAdapter.setFinishedLoading(it.size < 5)
            usersAdapter.addData(it)
        }

    }

    override fun onUserClick(user: UserBean) {

    }

    private fun resetState() {
        viewModel.clear()
        usersAdapter.clear()
        endlessRecyclerViewScrollListener.resetState()
        binding.shimmer.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
        currentPage = 1
        requestUsers()
    }
}