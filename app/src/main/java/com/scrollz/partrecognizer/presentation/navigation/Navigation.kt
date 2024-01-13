package com.scrollz.partrecognizer.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.scrollz.partrecognizer.presentation.main_screen.MainViewModel
import com.scrollz.partrecognizer.presentation.main_screen.components.MainScreen
import com.scrollz.partrecognizer.presentation.report_screen.ReportViewModel
import com.scrollz.partrecognizer.presentation.report_screen.components.ReportScreen
import com.scrollz.partrecognizer.presentation.settings_screen.SettingsViewModel
import com.scrollz.partrecognizer.presentation.settings_screen.components.SettingsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.Main.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(
                route = Destination.Main.route
            ) {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainState by mainViewModel.state.collectAsStateWithLifecycle()
                val reports = mainViewModel.reports.collectAsLazyPagingItems()
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = mainState,
                    reports = reports,
                    onEvent = mainViewModel::onEvent,
                    navigateToSettings = {
                        navController.navigate(Destination.Settings.route) {
                            launchSingleTop = true
                        }
                    },
                    onReportClick = { reportID ->
                        navController.navigate(Destination.Report.route + "/$reportID") {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = Destination.Report.route + "/{reportID}",
                arguments = listOf(navArgument(name = "reportID") { type = NavType.LongType }),
                enterTransition = {
                    fadeIn(tween(300)) +
                    slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { fullHeight -> fullHeight/2 }
                    )
                },
                exitTransition = {
                    fadeOut(tween(300)) +
                    slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { fullHeight -> fullHeight/2 }
                    )
                }
            ) {
                val reportViewModel = hiltViewModel<ReportViewModel>()
                val repostState by reportViewModel.state.collectAsStateWithLifecycle()
                ReportScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = repostState,
                    onEvent = reportViewModel::onEvent,
                    navigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Destination.Settings.route,
                enterTransition = {
                    fadeIn(tween(300)) + slideInHorizontally(tween(300))
                },
                exitTransition = {
                    fadeOut(tween(300)) + slideOutHorizontally(tween(300))
                }
            ) {
                val settingsViewModel = hiltViewModel<SettingsViewModel>()
                val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
                SettingsScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = settingsState,
                    onEvent = settingsViewModel::onEvent,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
