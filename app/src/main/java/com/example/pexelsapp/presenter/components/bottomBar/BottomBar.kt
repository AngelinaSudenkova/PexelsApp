package com.example.pexelsapp.presenter.components.bottomBar


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarPexels(navController: NavController) {

    val navigationSelectedItem = rememberSaveable { mutableIntStateOf(0) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.destination?.route?.let { route ->
            val newIndex =
                BottomBarTab.getBottomNavigationItems().indexOfFirst { it.getRoute() == route }
            if (newIndex != -1) {
                navigationSelectedItem.intValue = newIndex
            }
        }
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BottomBarTab.getBottomNavigationItems().forEachIndexed { index, bottomBarTab ->
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = if (navigationSelectedItem.intValue == index) {
                                painterResource(id = bottomBarTab.iconPathSelected)
                            } else {
                                painterResource(id = bottomBarTab.iconPathNotSelected)
                            },
                            contentDescription = bottomBarTab.title,
                            tint = if (navigationSelectedItem.intValue == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                },
                label = {
                    bottomBarTab.title
                },
                selected = navigationSelectedItem.intValue == index,
                onClick = {
                    if (navigationSelectedItem.intValue != index) {
                        navigationSelectedItem.intValue = index
                        navController.navigate(bottomBarTab.getRoute()) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


