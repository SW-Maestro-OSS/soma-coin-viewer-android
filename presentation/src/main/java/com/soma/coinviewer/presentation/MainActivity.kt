package com.soma.coinviewer.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.soma.coinviewer.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val hideBottomNavDestinationIds: Set<Int> by lazy {
        resources.obtainTypedArray(R.array.hideBottomNavDestinationIds).use { typedArray ->
            (0 until typedArray.length())
                .mapTo(mutableSetOf()) { index -> typedArray.getResourceId(index, 0) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
    }

    private fun setNavigation() {
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.main_FCV) as NavHostFragment
            navController = navHostFragment.navController
            mainBNV.itemIconTintList = null
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.mainBNV.visibility = if (destination.id in hideBottomNavDestinationIds) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}