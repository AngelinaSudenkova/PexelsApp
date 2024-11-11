package com.example.pexelsapp.presenter.screens.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.usecase.GetAllMarkedPhotosUC
import com.example.pexelsapp.domain.model.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val getAllMarkedPhotos: GetAllMarkedPhotosUC
) : ViewModel() {

    private val _data = mutableStateOf(DataOrException<List<PhotoEntity>, Boolean, Exception>())
    val data: State<DataOrException<List<PhotoEntity>, Boolean, Exception>> = _data

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            _data.value = DataOrException(loading = true)
            try {
                getAllMarkedPhotos().collect { photos ->
                    _data.value = DataOrException(data = photos, loading = false)
                }
            } catch (e: Exception) {
                _data.value = DataOrException(data = emptyList(), loading = false, e = e)
            }
        }
    }
}
