package com.example.pexelsapp.presenter.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pexelsapp.R
import com.example.pexelsapp.presenter.components.FeaturedCategoriesRow
import com.example.pexelsapp.presenter.components.PhotoGrid
import com.example.pexelsapp.presenter.components.SearchInputField
import com.example.pexelsapp.presenter.screens.PexelsScreens


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val data = viewModel.data.value
    val featureCollections = viewModel.featureCollections.value
    val searchState = remember { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            SearchInputField(
                searchState = searchState,
                onSearch = {
                    viewModel.getPhotosByCategory(searchState.value, 1, 80)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            featureCollections.data?.let { categories ->
                FeaturedCategoriesRow(
                    categories = categories,
                    onCategoryClick = { category ->
                        searchState.value = category.searchString
                        viewModel.getPhotosByCategory(category.searchString, 1, 80)
                    }
                )
            }

            when {
                data.loading == true -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    )
                }

                data.data.isNullOrEmpty() && data.e == null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.no_data_available))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.explore),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(
                                onClick = {
                                    viewModel.getCuratedPhotos()
                                }
                            )
                        )
                    }
                }

                data.e != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.no_network_icon),
                            contentDescription = stringResource(R.string.error),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.try_again),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(
                                onClick = {
                                    if (searchState.value.isEmpty()) {
                                        viewModel.getCuratedPhotos()
                                    } else {
                                        viewModel.getPhotosByCategory(searchState.value, 1, 80)
                                    }
                                }
                            )
                        )
                    }
                }

                else -> {
                    Spacer(modifier = Modifier.height(24.dp))
                    data.data?.let { photos ->
                        PhotoGrid(photos = photos) {
                            navController.navigate(PexelsScreens.DetailScreen.name + "/${it.id}")
                        }
                    }
                }
            }
        }
    }
}
