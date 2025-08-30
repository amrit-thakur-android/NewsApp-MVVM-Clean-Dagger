package com.amritthakur.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.presentation.navigation.NavigationCoordinator
import com.amritthakur.newsapp.presentation.navigation.Screen
import com.amritthakur.newsapp.presentation.screen.HomeScreen
import com.amritthakur.newsapp.presentation.screen.NewsScreen
import com.amritthakur.newsapp.presentation.screen.SourcesScreen

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

        composable(Screen.Home.route) {
            val homeViewModel = remember { applicationComponent.homeViewModel() }
            HomeScreen(
                input = homeViewModel,
                output = homeViewModel
            )
        }

        composable(
            route = "news?source={source}&country={country}&language={language}",
            arguments = listOf(
                navArgument("source") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("country") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("language") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val source = backStackEntry.arguments?.getString("source")
            val country = backStackEntry.arguments?.getString("country")
            val language = backStackEntry.arguments?.getString("language")

            val newsViewModel = remember {
                applicationComponent.newsViewModel().apply {
                    updateParams(source, country, language)
                }
            }
            NewsScreen(
                input = newsViewModel,
                output = newsViewModel
            )
        }

        composable(Screen.Sources.route) {
            val sourcesViewModel = remember { applicationComponent.sourcesViewModel() }
            SourcesScreen(
                input = sourcesViewModel,
                output = sourcesViewModel
            )
        }
    }
}