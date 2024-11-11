package com.example.pexelsapp.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomPhotoEntity::class, RoomMarkedPhotoEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}