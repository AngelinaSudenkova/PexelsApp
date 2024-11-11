package com.example.pexelsapp.data.repository.remote

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosRemoteDataSource @Inject constructor(
    private val photosApiService: PhotosApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getPhotosByCategory(category: String, page: Int, perPage: Int) =
        withContext(ioDispatcher) {
            photosApiService.searchPhotosByCategory(category, page, perPage)
        }

    suspend fun getPhotoById(id: Int) = withContext(ioDispatcher) {
        photosApiService.getPhotoById(id)
    }

    suspend fun getFeaturedCollections(page: Int = 1, perPage: Int = 7): CollectionPhotoResponse =

        withContext(ioDispatcher) {
            photosApiService.getFeaturedCollections(page, perPage)
        }

    suspend fun getCuratedPhotos(page: Int = 1, perPage: Int = 30): CuratedPhotoResponse =
        withContext(ioDispatcher) {
            photosApiService.getCuratedPhotos(page, perPage)
        }
}

