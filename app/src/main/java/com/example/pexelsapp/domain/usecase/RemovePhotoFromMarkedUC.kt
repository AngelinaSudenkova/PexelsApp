package com.example.pexelsapp.domain.usecase

import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.repository.PhotoRepository
import javax.inject.Inject

class RemovePhotoFromMarkedUC @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photo: PhotoEntity) {
        repository.removePhotoFromMarked(photo)
    }
}