package com.example.pexelsapp.presenter.screens.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pexelsapp.R
import com.example.pexelsapp.presenter.components.MarkedPhotoGrid
import com.example.pexelsapp.presenter.screens.PexelsScreens

@Composable
fun BookMarkScreen(
    navController: NavController,
    viewModel: BookMarkViewModel = hiltViewModel()
) {
    val dataOrException = viewModel.data.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.bookmarks),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                dataOrException.loading == true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                dataOrException.e != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(
                                R.string.error_loading_bookmarks) + " " +
                                dataOrException.e!!.localizedMessage
                            ,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                dataOrException.data.isNullOrEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.you_haven_t_saved_anything_yet),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.explore),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(onClick = { navController.navigate(PexelsScreens.HomeScreen.name){
                                popUpTo(0)
                            } })
                        )
                    }
                }

                else -> {
                    MarkedPhotoGrid(
                        photos = dataOrException.data!!,
                        onPhotoClick = { navController.navigate(PexelsScreens.DetailScreen.name + "/${it.id}") })
                }
            }
        }
    }
}

