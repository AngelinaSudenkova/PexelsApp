package com.example.pexelsapp.domain.usecase

import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosByCategoryUC @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(category: String, page: Int, perPage: Int): Flow<List<PhotoEntity>> {
        return repository.getPhotosByCategory(category.trim(), page, perPage)
    }

}