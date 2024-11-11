package com.example.pexelsapp.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<RoomPhotoEntity>)

    @Query("SELECT * FROM photo WHERE searchQuery = :query")
    suspend fun getPhotosByCategory(query: String): List<RoomPhotoEntity>

    @Query("DELETE FROM photo WHERE searchQuery = :query")
    suspend fun deletePhotosByCategory(query: String)

    @Query("SELECT * FROM photo WHERE id = :id")
    suspend fun getPhotoById(id: Int): RoomPhotoEntity?

    @Query("SELECT * FROM marked_photo")
    fun getMarkedPhotos(): Flow<List<RoomMarkedPhotoEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkedPhoto(markedPhoto: RoomMarkedPhotoEntity)

    @Delete
    suspend fun deleteMarkedPhoto(markedPhoto: RoomMarkedPhotoEntity)


    @Query("SELECT EXISTS(SELECT * FROM marked_photo WHERE id = :id)")
     suspend fun checkIfExist(id: Int): Boolean

    @Query("SELECT * FROM photo WHERE searchQuery = 'curated'")
    fun getCuratedPhotos(): List<RoomPhotoEntity>

}