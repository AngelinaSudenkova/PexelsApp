package com.example.pexelsapp.presenter.components.bottomBar


import com.example.pexelsapp.R
import com.example.pexelsapp.presenter.screens.PexelsScreens

sealed class BottomBarTab(
    val title: String,
    val iconPathNotSelected: Int,
    val iconPathSelected: Int
) {
    data object Home : BottomBarTab(
        title = "Home",
        iconPathNotSelected = R.drawable.home_icon_outlined,
        iconPathSelected = R.drawable.home_icon_filled
    )

    data object BookMark : BottomBarTab(
        title = "Bookmark",
        iconPathNotSelected = R.drawable.bookmark_icon_outlined,
        iconPathSelected = R.drawable.bookmark_icon_filled
    )

    fun getRoute(): String {
        return when (this) {
            is Home -> PexelsScreens.HomeScreen.name
            is BookMark -> PexelsScreens.BookmarkScreen.name
        }
    }

    companion object {
        fun getBottomNavigationItems(): List<BottomBarTab> {
            return listOf(Home, BookMark)
        }
    }
}
