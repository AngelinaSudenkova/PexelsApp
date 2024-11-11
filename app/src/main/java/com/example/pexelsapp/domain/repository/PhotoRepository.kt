package com.example.pexelsapp.domain.repository

import com.example.pexelsapp.data.repository.remote.CollectionPhoto
import com.example.pexelsapp.domain.model.FeatureEntity
import com.example.pexelsapp.domain.model.PhotoEntity
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun getPhotosByCategory(category: String, page: Int, perPage: Int): Flow<List<PhotoEntity>>
    suspend fun addPhotoToMarked(photo: PhotoEntity)
    suspend fun removePhotoFromMarked(photo: PhotoEntity)
    suspend fun getPhotoById(photoId: Int) : PhotoEntity
    suspend fun getAllMarkedPhotos(): Flow<List<PhotoEntity>>
    suspend fun getFeaturedCollections(): List<FeatureEntity>
    suspend fun getCuratedPhotos(): Flow<List<PhotoEntity>>
}