package com.soma.coinviewer.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.soma.coinviewer.common_ui.repeatOnStarted
import com.soma.coinviewer.navigation.NavigationTarget
import com.soma.coinviewer.navigation.deepLinkNavigateTo
import com.soma.coinviewer.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
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
        observeViewModel()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.connectWebSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnectWebsocket()
    }

    private fun setNavigation() = binding.apply {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_FCV) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBNV.setupWithNavController(navController)
        mainBNV.itemIconTintList = null

        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainBNV.visibility = if (destination.id in hideBottomNavDestinationIds) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun observeViewModel() = viewModel.apply {
        repeatOnStarted {
            navigationFlow.collect { handleNavigationEvent(it) }
        }
    }

    private fun handleNavigationEvent(navigationTarget: NavigationTarget) {
        navController.deepLinkNavigateTo(
            context = this@MainActivity,
            deepLinkRoute = navigationTarget.destination,
            popUpTo = navigationTarget.popUpTo
        )
    }
}