package com.example.pexelsapp.data.repository.remote

import com.example.pexelsapp.data.dto.CuratedPhotoDto

data class CuratedPhotoResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<CuratedPhotoDto>
)