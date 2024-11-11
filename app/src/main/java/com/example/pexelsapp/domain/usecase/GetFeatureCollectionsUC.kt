package com.example.pexelsapp.domain.usecase

import android.util.Log
import com.example.pexelsapp.domain.model.FeatureEntity
import com.example.pexelsapp.domain.repository.PhotoRepository
import javax.inject.Inject

class GetFeatureCollectionsUC @Inject constructor(
   private val repository: PhotoRepository
) {
    suspend operator fun invoke(): List<FeatureEntity> {
        val collections = repository.getFeaturedCollections()
        Log.d("getFeatureCollectionsUC", "Returned collections: $collections")
        return collections
    }
}