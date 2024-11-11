package com.example.pexelsapp.data.repository.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PhotosLocalDataSource @Inject constructor(
    private val photoDao: PhotoDao
) {
    suspend fun insertPhotos(photos: List<RoomPhotoEntity>) {
        photoDao.insertPhotos(photos)
    }

    suspend fun getPhotosByCategory(query: String): List<RoomPhotoEntity> {
        return photoDao.getPhotosByCategory(query)
    }

    suspend fun deletePhotosByCategory(query: String) {
        photoDao.deletePhotosByCategory(query)
    }

    suspend fun getMarkedPhotos(): Flow<List<RoomMarkedPhotoEntity>> {
        return photoDao.getMarkedPhotos()
    }

    suspend fun insertMarkedPhoto(markedPhoto: RoomMarkedPhotoEntity) {
        photoDao.insertMarkedPhoto(markedPhoto)
    }

    suspend fun deleteMarkedPhoto(markedPhoto: RoomMarkedPhotoEntity) {
        photoDao.deleteMarkedPhoto(markedPhoto)
    }

    suspend fun getPhotoById(id: Int): RoomPhotoEntity? {
        return photoDao.getPhotoById(id)
    }

    suspend fun checkIfMarkedExist(id: Int): Boolean {
        return photoDao.checkIfExist(id)
    }

    suspend fun getCuratedPhotos(): List<RoomPhotoEntity> {
        return photoDao.getCuratedPhotos()
    }
}