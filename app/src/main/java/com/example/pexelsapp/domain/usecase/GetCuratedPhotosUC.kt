package com.example.pexelsapp.domain.usecase

import com.example.pexelsapp.domain.repository.PhotoRepository
import javax.inject.Inject

class GetCuratedPhotosUC @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke() = photoRepository.getCuratedPhotos()
}