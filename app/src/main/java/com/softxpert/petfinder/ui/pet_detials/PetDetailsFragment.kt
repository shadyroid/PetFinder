package com.softxpert.petfinder.ui.pet_detials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.softxpert.domain.entity.beans.PetBean
import com.softxpert.petfinder.R
import com.softxpert.petfinder.databinding.FragmentPetDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetDetailsFragment : Fragment() {
    private var _binding: FragmentPetDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var petBean: PetBean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = PetDetailsFragmentArgs.fromBundle(requireArguments())
        petBean = args.pet

        setData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        Glide.with(this)
            .load(petBean.displayMediumImage)
            .placeholder(R.drawable.app_logo)
            .error(R.drawable.app_logo)
            .into(binding.ivImage)
        binding.tvName.text = getString(R.string.name_is,petBean.displayName)
        binding.tvSize.text = getString(R.string.size_is,petBean.displaySize)
        binding.tvColor.text = getString(R.string.color_is,petBean.displayColor)
        binding.tvAddress.text = getString(R.string.address_is,petBean.displayAddress)
        binding.btnPetWebsite.setOnClickListener { openLink(petBean.url!!) }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(browserIntent)
    }
}