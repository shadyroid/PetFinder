package com.shady.boyot.ui.edit_user

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.utils.AppHelper
import com.shady.boyot.databinding.FragmentEditUserBinding
import com.shady.boyot.ui.MainViewModel
import com.shady.domain.entity.responses.UserDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


@AndroidEntryPoint
class EditUserFragment : BaseFragment() {
    private var part: MultipartBody.Part? = null
    private val viewModel: EditUserViewModel by viewModels()
    private lateinit var binding: FragmentEditUserBinding
    private lateinit var userId: String


    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentEditUserBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        initArguments()
        initObserves()
        setOnClickListener()

        requestUserDetails()
    }


    private fun initArguments() {
        val args = EditUserFragmentArgs.fromBundle(requireArguments())
        userId = args.userId
    }

    private fun setOnClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            popBackStack()
        }
        binding.ivHeaderLogo.setOnClickListener {
            navigate(R.id.global_action_back_to_users_search);
        }
        binding.ivEditEmail.setOnClickListener {
            binding.etEmail.isEnabled = true
            binding.etEmail.requestFocus()
            binding.btnSaveChanges.visibility = VISIBLE
            binding.btnBack.visibility = VISIBLE
        }
        binding.ivEditClientPhone.setOnClickListener {
            binding.etClientPhone.isEnabled = true
            binding.etClientPhone.requestFocus()
            binding.btnSaveChanges.visibility = VISIBLE
            binding.btnBack.visibility = VISIBLE
        }
        binding.ivEditNationalId.setOnClickListener {
            binding.etNationalId.isEnabled = true
            binding.etNationalId.requestFocus()
            binding.btnSaveChanges.visibility = VISIBLE
            binding.btnBack.visibility = VISIBLE
        }

        binding.btnBack.setOnClickListener {
            stopEditMode()
        }
        binding.btnSaveChanges.setOnClickListener {
            stopEditMode()
            requestEditUser()
        }

        binding.ivTakeAPicture.setOnClickListener {
            navigate(EditUserFragmentDirections.actionNavEditUserToNavCamera())
        }
        binding.tvTakeAPicture.setOnClickListener {
            navigate(EditUserFragmentDirections.actionNavEditUserToNavCamera())
        }
    }

    private fun stopEditMode() {
        binding.etNationalId.clearFocus()
        binding.etClientPhone.clearFocus()
        binding.etEmail.clearFocus()
        binding.etClientPhone.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.etNationalId.isEnabled = false
        binding.btnSaveChanges.visibility = GONE
        binding.btnBack.visibility = GONE
    }

    private fun initObserves() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.onCameraTakePictureMutableStateFlow.collect {
                if (it != null) onCameraTakePicture(it)
            }
        }

        lifecycleScope.launch { viewModel.apiErrorMutableStateFlow.collect { onApiError(it) } }
        lifecycleScope.launch { viewModel.loadingMutableStateFlow.collect { onLoading(it) } }
        lifecycleScope.launch {
            viewModel.editUserResponseMutableStateFlow.collect { if (it != null) onEditUserResponse() }
        }
        lifecycleScope.launch {
            viewModel.userDetailsResponseMutableStateFlow.collect {
                if (it != null) onUserDetailsResponse(
                    it
                )
            }
        }
    }

    private fun onCameraTakePicture(imagePath: String) {
        initImage(imagePath)
        binding.btnSaveChanges.visibility = VISIBLE
        binding.btnBack.visibility = VISIBLE
    }

    private fun initImage(path: String) {
        Glide.with(requireContext())
            .asBitmap()
            .load(path)
            .apply(RequestOptions().override(600, 200))
            .skipMemoryCache(true)
            .listener(object : RequestListener<Bitmap?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any,
                    target: Target<Bitmap?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    part = AppHelper.getMultipartBodyImage(
                        requireContext(),
                        resource,
                        "client_national_image"
                    )
                    binding.ivNationalIdImage.visibility = VISIBLE
                    binding.ivNationalIdImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun requestUserDetails() {
        val map = HashMap<String, String>()
        map["user_id"] = userId;
        viewModel.requestUserDetails(map)
    }

    fun requestEditUser() {
        val map = HashMap<String, RequestBody>()
        map["client_name"] = AppHelper.toRequestBody(binding.tvName.text.toString())
        map["client_email"] = AppHelper.toRequestBody(binding.etEmail.text.toString())
        map["client_phone"] = AppHelper.toRequestBody(binding.etClientPhone.text.toString())
        map["client_national_id"] = AppHelper.toRequestBody(binding.etNationalId.text.toString())
        viewModel.requestEditUser(userId, map, part)
    }


    private fun onEditUserResponse() {
    }

    private fun onUserDetailsResponse(response: UserDetailsResponse) {
        binding.tvName.text = response.data?.name
        binding.etClientPhone.setText(response.data?.tel1)
        binding.etEmail.setText(response.data?.email)
        binding.etNationalId.setText(response.data?.nationalid)
        if (response.data?.national_image.isNullOrEmpty()) {
            binding.ivNationalIdImage.visibility = GONE
        } else {
            binding.ivNationalIdImage.visibility = VISIBLE
            Glide.with(requireContext()).load(response.data?.national_image)
                .into(binding.ivNationalIdImage)

        }

    }


}