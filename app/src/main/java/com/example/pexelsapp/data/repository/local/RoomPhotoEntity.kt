package com.example.pexelsapp.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pexelsapp.domain.model.PhotoEntity

@Entity(tableName = "photo")
data class RoomPhotoEntity(
    @PrimaryKey val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val avgColor: String,
    val src: String,
    var searchQuery: String,
    val timestamp: Long = System.currentTimeMillis()
){
    fun toPhotoEntity() = PhotoEntity(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        avgColor = avgColor,
        searchString = searchQuery,
        isMarked = false
    )
}