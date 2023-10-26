package com.shady.boyot.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.shady.boyot.R
import com.shady.boyot.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

    }
    fun setLanguage() {
        val locale = Locale("ar")
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.setLayoutDirection(locale)
        configuration.locale = locale
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

}