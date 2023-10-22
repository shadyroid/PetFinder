package com.shady.boyot.ui.no_internet_connection

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.databinding.FragmentNoInternetConnectionBinding

class NoInternetConnectionFragment : BaseFragment() {
    private lateinit var binding: FragmentNoInternetConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentNoInternetConnectionBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        binding.btnRetry.setOnClickListener {
            if (isInternetAvailable) {
                popBackStack()
            }
        }
    }
}