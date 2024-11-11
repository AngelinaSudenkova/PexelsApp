package com.example.pexelsapp.presenter.screens.detail

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.usecase.AddPhotoToMarkedUC
import com.example.pexelsapp.domain.usecase.GetPhotoByIdUC
import com.example.pexelsapp.domain.usecase.RemovePhotoFromMarkedUC
import com.example.pexelsapp.domain.model.DataOrException
import com.example.pexelsapp.utils.saveImageToGallery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPhotoByIdUC: GetPhotoByIdUC,
    private val addPhotoToMarkedUC: AddPhotoToMarkedUC,
    private val removePhotoFromMarkedUC: RemovePhotoFromMarkedUC
) : ViewModel() {

    private val _data: MutableState<DataOrException<PhotoEntity, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, null))
    val data : MutableState<DataOrException<PhotoEntity, Boolean, Exception>> = _data

    private val _isPhotoMarked = mutableStateOf(false)
    val isPhotoMarked: MutableState<Boolean> = _isPhotoMarked

    val downloadStatus = mutableStateOf<String?>(null)

    fun initialize(photoId: Int) {
        viewModelScope.launch {
            try {
                val photo = getPhotoByIdUC(photoId)
                _data.value = DataOrException(photo, false, null)
                _isPhotoMarked.value = photo.isMarked
            } catch (e: Exception) {
                _data.value = DataOrException(PhotoEntity(), false, e)
            }
        }
    }

    fun addPhotoToMarked(photo: PhotoEntity) {
        viewModelScope.launch {
            addPhotoToMarkedUC(photo)
            _isPhotoMarked.value = true
        }
    }

    fun removePhotoFromMarked(photo: PhotoEntity) {
        viewModelScope.launch {
            removePhotoFromMarkedUC(photo)
            _isPhotoMarked.value = false
        }
    }

    fun downloadImage(context: Context, imageUrl: String) {
        viewModelScope.launch {
            val success = saveImageToGallery(context, imageUrl)
            downloadStatus.value = if (success) "Download successful" else "Download failed"
        }
    }
}