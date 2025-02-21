package com.soma.coinviewer.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.soma.coinviewer.common_ui.repeatOnStarted
import com.soma.coinviewer.i18n.I18NEvent
import com.soma.coinviewer.i18n.I18NHelper
import com.soma.coinviewer.navigation.NavigationEvent
import com.soma.coinviewer.navigation.NavigationHelper
import com.soma.coinviewer.navigation.deepLinkNavigateTo
import com.soma.coinviewer.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    @Inject
    lateinit var i18NHelper: I18NHelper

    @Inject
    lateinit var navigationHelper: NavigationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnStarted {
                i18NHelper.i18NEventBus.receiveAsFlow()
                    .collect {
                        when (it) {
                            I18NEvent.UpdateLanguage -> updateLanguage(isColdStart = false)
                        }
                    }
            }

            updateLanguage(isColdStart = true)
        }

        setNavigation()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
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
            navigationHelper.navigationFlow
                .collect { handleNavigationEvent(it) }
        }

        repeatOnStarted {
            errorHelper.errorFlow
                .collect { handleError(it) }
        }
    }

    private fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.To -> {
                navController.deepLinkNavigateTo(
                    context = this@MainActivity,
                    deepLinkRoute = navigationEvent.destination,
                    popUpTo = navigationEvent.popUpTo
                )
            }

            NavigationEvent.Up -> navController.navigateUp()
        }
    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT)
            .show()
    }

    private suspend fun updateLanguage(isColdStart: Boolean) {
        val region = i18NHelper.getRegion()
            .getOrElse {
                viewModel.errorHelper.sendError(it)
                return
            }
        val language = region.language

        val config = resources.configuration
        config.setLocale(language)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        if (!isColdStart) {
            recreate()
        }
    }
}
