package com.softxpert.petfinder.mvvm.pet_detials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.softxpert.petfinder.databinding.ActivityPetDetailsBinding

class PetDetailsActivity : AppCompatActivity() {
    private val viewModel: PetDetailsViewModel by viewModels()
    private lateinit var binding: ActivityPetDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}