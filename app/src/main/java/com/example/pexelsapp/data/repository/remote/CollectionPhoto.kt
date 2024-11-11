package com.example.pexelsapp.data.repository.remote

import com.example.pexelsapp.domain.model.FeatureEntity

data class CollectionPhoto(
    val description: String,
    val id: String,
    val media_count: Int,
    val photos_count: Int,
    val private: Boolean,
    val title: String,
    val videos_count: Int
){
    fun toFeatureEntity() = FeatureEntity(searchString = title)
}