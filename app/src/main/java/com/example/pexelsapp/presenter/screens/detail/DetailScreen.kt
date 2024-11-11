package com.example.pexelsapp.presenter.screens.detail

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pexelsapp.presenter.components.DetailTopBar
import com.example.pexelsapp.presenter.components.DownloadButton
import com.example.pexelsapp.presenter.components.PhotoItem
import com.example.pexelsapp.presenter.components.SaveButton
import com.example.pexelsapp.presenter.screens.PexelsScreens
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.pexelsapp.R


@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
    photoId: Int
) {
    viewModel.initialize(photoId)
    val data =  viewModel.data.value
    val isPhotoMarked by viewModel.isPhotoMarked
    val downloadStatus by viewModel.downloadStatus

    val context = LocalContext.current

    if (data.loading == true) {
        LinearProgressIndicator()
    } else if (data.data != null) {
        val photo = data.data!!
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                DetailTopBar(
                    photographerName = photo.photographer.split("+").getOrElse(0) { "" },
                    photographerSurname = photo.photographer.split("+").getOrElse(1) { "" },
                    onBackClick = { navController.popBackStack() })

                PhotoItem(photo = photo, isClickable = false) { }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    DownloadButton(onDownloadClick = { viewModel.downloadImage(context, photo.url) })
                    SaveButton(
                        onSaveClick = {
                            if (isPhotoMarked) {
                                viewModel.removePhotoFromMarked(photo)
                            } else {
                                viewModel.addPhotoToMarked(photo)
                            }
                        },
                        isSaved = isPhotoMarked
                    )
                }
            }

            downloadStatus?.let {
                Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                viewModel.downloadStatus.value = null
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.image_not_found),
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
}
