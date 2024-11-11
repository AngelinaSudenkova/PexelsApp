package com.example.pexelsapp.domain.usecase

import com.example.pexelsapp.domain.repository.PhotoRepository
import javax.inject.Inject

class GetAllMarkedPhotosUC @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke() = repository.getAllMarkedPhotos()
}