package com.example.pexelsapp.domain.model

import android.icu.text.StringSearch
import com.example.pexelsapp.data.repository.local.RoomMarkedPhotoEntity
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


data class PhotoEntity(
    val id: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val avgColor: String = "",
    var searchString : String = "",
    var isMarked: Boolean = false
) {

    fun toRoomMarkedPhotoEntity() = RoomMarkedPhotoEntity(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        avgColor = avgColor,
        src = url,
        searchQuery = searchString,
        timestamp = System.currentTimeMillis()
    )
}