package com.example.pexelsapp.data.repository.remote

import com.example.pexelsapp.data.dto.PhotoDto
import com.example.pexelsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotosApiService {
    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("v1/search")
    suspend fun searchPhotosByCategory(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PhotosResponse


    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("v1/photos")
    suspend fun getPhotoById(
        @Query("id") id: Int
    ): PhotoDto

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("v1/collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): CollectionPhotoResponse

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET("v1/curated")
    suspend fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): CuratedPhotoResponse
}