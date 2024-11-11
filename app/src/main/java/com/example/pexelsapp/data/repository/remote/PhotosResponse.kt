package com.example.pexelsapp.data.repository.remote

import com.example.pexelsapp.data.dto.PhotoDto

data class PhotosResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoDto>,
    val total_results: Int
)