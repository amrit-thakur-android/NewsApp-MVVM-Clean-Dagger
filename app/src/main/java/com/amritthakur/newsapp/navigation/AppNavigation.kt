package com.amritthakur.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.presentation.navigation.NavigationCoordinator
import com.amritthakur.newsapp.presentation.navigation.Screen

@Composable
fun AppNavigation(
    modifier: Modifier
) {
    val context = LocalContext.current
    val applicationComponent = remember {
        (context.applicationContext as NewsApplication).applicationComponent
    }

    val navController = rememberNavController()
    val navigationCoordinator = remember { NavigationCoordinator(navController) }

    val navigationChannel = remember { applicationComponent.navigationChannel() }

    val navigationEvent by navigationChannel.navigationEvent
        .collectAsStateWithLifecycle()

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            navigationCoordinator.handleNavigationEvent(event)
            navigationChannel.clearEvent()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

    }
}