package com.example.pexelsapp.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.model.FeatureEntity
import com.example.pexelsapp.domain.model.PhotoEntity
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun SearchInputField(
    searchState: MutableState<String>,
    label: String = stringResource(R.string.search),
    onSearch: () -> Unit,
    imeAction: ImeAction = ImeAction.Search
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = searchState.value,
            onValueChange = { searchState.value = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
            }),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                if (searchState.value.isEmpty()) {
                    Text(text = label, color = MaterialTheme.colorScheme.onSurface)
                }
                innerTextField()
            }
        )

        if (searchState.value.isNotEmpty()) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        searchState.value = ""
                    }
            )
        }
    }
}


@Composable
fun PhotoGrid(
    photos: List<PhotoEntity>,
    onPhotoClick: (PhotoEntity) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(photos) { photo ->
                PhotoItem(photo = photo, onPhotoClick = onPhotoClick, isClickable = true)
            }
        }
    )
}

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    photo: PhotoEntity,
    isClickable: Boolean,
    onPhotoClick: (PhotoEntity) -> Unit
) {

    SubcomposeAsyncImage(
        model = photo.url,
        contentDescription = photo.photographer,
        modifier = modifier
            .aspectRatio(photo.width.toFloat() / photo.height)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(
                if (isClickable) Modifier.clickable { onPhotoClick(photo) }
                else Modifier
            ),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        },
        error = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = stringResource(R.string.problem_icon),
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.Center),
                    tint = Color.Red.copy(0.8f)
                )
            }
        }

    )
}

@Composable
fun MarkedPhotoGrid(
    photos: List<PhotoEntity>,
    onPhotoClick: (PhotoEntity) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(photos) { photo ->
                MarkedPhotoItem(photo = photo, onPhotoClick = onPhotoClick, isClickable = true)
            }
        }
    )
}

@Composable
fun MarkedPhotoItem(
    modifier: Modifier = Modifier,
    photo: PhotoEntity,
    isClickable: Boolean,
    onPhotoClick: (PhotoEntity) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(photo.width.toFloat() / photo.height)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(
                if (isClickable) Modifier.clickable { onPhotoClick(photo) }
                else Modifier
            )
    ) {
        SubcomposeAsyncImage(
            model = photo.url,
            contentDescription = photo.photographer,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = stringResource(R.string.problem_icon),
                        modifier = Modifier
                            .size(45.dp)
                            .align(Alignment.Center),
                        tint = Color.Red.copy(0.8f)
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(8.dp)
        ) {
            Text(
                text = photo.photographer,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



@Composable
fun DetailTopBar(
    photographerName: String,
    photographerSurname: String,
    onBackClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "$photographerName $photographerSurname",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 8.dp),
            )
        }
    }
}

@Composable
fun DownloadButton(onDownloadClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = CircleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable(onClick = onDownloadClick)
        ) {
            IconButton(
                onClick = onDownloadClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download_icon),
                    contentDescription = "Download",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.download),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun SaveButton(onSaveClick: () -> Unit, isSaved: Boolean = false) {
    IconButton(
        onClick = onSaveClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            painter = if (!isSaved) painterResource(id = R.drawable.bookmark_icon_outlined)
            else painterResource(id = R.drawable.bookmark_icon_filled),
            contentDescription = stringResource(R.string.save),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun FeaturedCategoriesRow(
    categories: List<FeatureEntity>,
    onCategoryClick: (FeatureEntity) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(categories) { category ->
            Text(
                text = category.searchString,
                modifier = Modifier
                    .background(
                        color = if (category.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .clickable { onCategoryClick(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = if (category.isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
