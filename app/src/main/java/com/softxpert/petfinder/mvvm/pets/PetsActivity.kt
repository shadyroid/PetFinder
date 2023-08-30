package com.softxpert.petfinder.mvvm.pets

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.softxpert.petfinder.databinding.ActivityPetsBinding

class PetsActivity : AppCompatActivity() {
    private val viewModel: PetsViewModel by viewModels()
    private lateinit var binding: ActivityPetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}