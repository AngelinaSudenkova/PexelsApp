package com.example.pexelsapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import com.example.pexelsapp.presenter.screens.splash.SplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.pexelsapp.presenter.components.bottomBar.BottomBarPexels
import com.example.pexelsapp.presenter.screens.PexelsScreens
import com.example.pexelsapp.presenter.screens.bookmark.BookMarkScreen
import com.example.pexelsapp.presenter.screens.detail.DetailScreen
import com.example.pexelsapp.presenter.screens.home.HomeScreen

@Composable
fun PexelsNavigation() {
    val navController = rememberNavController()

    val isShowed = navController.currentBackStackEntryAsState().value?.destination?.route in listOf(
        PexelsScreens.HomeScreen.name,
        PexelsScreens.BookmarkScreen.name
    )

    Scaffold(
        bottomBar = { if (isShowed) BottomBarPexels(navController) }
    ) {
        NavHost(
            navController = navController, startDestination = PexelsScreens.SplashScreen.name,
            modifier = Modifier.padding(it)
        ) {
            composable(PexelsScreens.SplashScreen.name) {
                SplashScreen(navController = navController)
            }
            composable(PexelsScreens.HomeScreen.name) {
                HomeScreen(navController = navController)
            }
            composable(
                route = "${PexelsScreens.DetailScreen.name}/{photoId}",
                arguments = listOf(navArgument("photoId") { type = NavType.IntType })
            ) {navBack ->
                 navBack.arguments?.getInt("photoId")?.let {
                    photoId -> DetailScreen(navController = navController, photoId = photoId)
                }

            }
            composable(PexelsScreens.BookmarkScreen.name) {
                BookMarkScreen(navController = navController)
            }
        }
    }
}


