package com.softxpert.petfinder.mvvm.pet_detials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.softxpert.petfinder.R
import com.softxpert.petfinder.classes.others.CONSTANTS.INTENT.OBJECT
import com.softxpert.petfinder.classes.rest.models.beans.PetBean
import com.softxpert.petfinder.databinding.ActivityPetDetailsBinding

class PetDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailsBinding
    var petBean: PetBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        petBean = intent.getParcelableExtra(OBJECT)

        setData()
    }

    private fun setData() {
        Glide.with(this)
            .load(petBean?.displayMediumImage)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.ivImage)
        binding.tvName.text = "${getString(R.string.name)}: ${petBean?.displayName}"
        binding.tvSize.text = "${getString(R.string.size)}: ${petBean?.displaySize}"
        binding.tvColor.text = "${getString(R.string.color)}${petBean?.displayColor}"
        binding.tvAddress.text = "${getString(R.string.address)}${petBean?.displayAddress}"
        binding.btnPetWebsite.setOnClickListener { openLink(petBean?.url!!) }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(browserIntent)
    }
}