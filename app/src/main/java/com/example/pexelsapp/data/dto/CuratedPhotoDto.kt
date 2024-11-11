package com.example.pexelsapp.data.dto

import com.example.pexelsapp.data.repository.local.RoomPhotoEntity
import com.example.pexelsapp.data.repository.remote.Src
import com.example.pexelsapp.domain.model.PhotoEntity

data class CuratedPhotoDto(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: Src,
    val url: String,
    val width: Int
){
    fun toPhotoEntity() = PhotoEntity(
        id = id,
        width = width,
        height = height,
        url = src.medium,
        photographer = photographer,
        avgColor = avg_color,
        isMarked = false
    )

    fun toRoomPhotoEntity() = RoomPhotoEntity(
        id = id,
        width = width,
        height = height,
        url = src.medium,
        photographer = photographer,
        avgColor = avg_color,
        src = src.medium,
        searchQuery = "",
        timestamp = System.currentTimeMillis()
    )
}