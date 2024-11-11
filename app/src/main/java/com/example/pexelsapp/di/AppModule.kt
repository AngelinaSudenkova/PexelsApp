package com.example.pexelsapp.di

import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.repository.PhotoRepositoryImpl
import com.example.pexelsapp.data.repository.local.PhotoDatabase
import com.example.pexelsapp.data.repository.local.PhotosLocalDataSource
import com.example.pexelsapp.data.repository.remote.PhotosApiService
import com.example.pexelsapp.data.repository.remote.PhotosRemoteDataSource
import com.example.pexelsapp.domain.repository.PhotoRepository
import com.example.pexelsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePexelsApiService(retrofit: Retrofit): PhotosApiService {
        return retrofit.create(PhotosApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PhotoDatabase  =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo_database"
        ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(database: PhotoDatabase) = database.photoDao()

    @Provides
    @Singleton
    fun providePhotoRepository(
        remoteDataSource: PhotosRemoteDataSource,
        localDataSource: PhotosLocalDataSource
    ): PhotoRepository {
        return PhotoRepositoryImpl(remoteDataSource, localDataSource)
    }


    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}