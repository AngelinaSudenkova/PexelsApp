package com.example.pexelsapp.data.repository.remote

data class CollectionPhotoResponse(
    val collections: List<CollectionPhoto>,
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val prev_page: String,
    val total_results: Int
)