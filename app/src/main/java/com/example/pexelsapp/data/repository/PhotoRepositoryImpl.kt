package com.example.pexelsapp.data.repository

import android.util.Log
import com.example.pexelsapp.data.repository.local.PhotosLocalDataSource
import com.example.pexelsapp.data.repository.remote.CollectionPhoto
import com.example.pexelsapp.data.repository.remote.PhotosRemoteDataSource
import com.example.pexelsapp.domain.model.FeatureEntity
import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
) : PhotoRepository {

    override suspend fun getPhotosByCategory(
        category: String,
        page: Int,
        perPage: Int
    ): Flow<List<PhotoEntity>> = flow {
        val localPhotos = localDataSource.getPhotosByCategory(category)
        if (localPhotos.isNotEmpty()) {
            emit(localPhotos.map {
                it.toPhotoEntity().apply {
                    isMarked = localDataSource.checkIfMarkedExist(it.id)
                }
            })
        } else {

            val photosResponse =
                remoteDataSource.getPhotosByCategory(category, page, perPage).photos
            localDataSource.insertPhotos(photosResponse.map { photoDto ->
                photoDto.toRoomPhotoEntity().apply {
                    searchQuery = category
                }
            })

            val photos = photosResponse.map {
                it.toPhotoEntity().apply {
                    isMarked = localDataSource.checkIfMarkedExist(it.id)
                }
            }
            emit(photos)
        }
    }

    override suspend fun addPhotoToMarked(photo: PhotoEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertMarkedPhoto(photo.toRoomMarkedPhotoEntity())
        }
    }

    override suspend fun removePhotoFromMarked(photo: PhotoEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteMarkedPhoto(photo.toRoomMarkedPhotoEntity())
        }
    }

    override suspend fun getPhotoById(photoId: Int): PhotoEntity {
        var photo = localDataSource.getPhotoById(photoId)?.toPhotoEntity()
        if (photo == null) {
            photo = remoteDataSource.getPhotoById(photoId).toPhotoEntity()
        }

        photo.isMarked = localDataSource.checkIfMarkedExist(photoId)

        return photo
    }

    override suspend fun getAllMarkedPhotos(): Flow<List<PhotoEntity>> {
        return localDataSource.getMarkedPhotos().map { markedPhotoEntityList ->
            markedPhotoEntityList.map { it.toPhotoEntity() }
        }
    }

    override suspend fun getFeaturedCollections(): List<FeatureEntity> {
        val featured = remoteDataSource.getFeaturedCollections().collections.map { collection ->
            collection.toFeatureEntity()
        }
        return featured
    }

    override suspend fun getCuratedPhotos(): Flow<List<PhotoEntity>> = flow {

        val localPhotos = withContext(Dispatchers.IO) { localDataSource.getCuratedPhotos() }
        if (localPhotos.isNotEmpty()) {
            emit(localPhotos.map {
                it.toPhotoEntity().apply {
                    isMarked = localDataSource.checkIfMarkedExist(it.id)
                }
            })
        } else {
            val photosResponse = remoteDataSource.getCuratedPhotos().photos
            localDataSource.insertPhotos(photosResponse.map { photoDto ->
                photoDto.toRoomPhotoEntity().apply {
                    searchQuery = "curated"
                }
            })

            val photos = photosResponse.map {
                it.toPhotoEntity().apply {
                    isMarked = localDataSource.checkIfMarkedExist(it.id)
                }
            }
            emit(photos)
        }
    }

}