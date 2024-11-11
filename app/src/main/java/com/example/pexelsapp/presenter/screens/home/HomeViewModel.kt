package com.example.pexelsapp.presenter.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.DataOrException
import com.example.pexelsapp.domain.model.FeatureEntity
import com.example.pexelsapp.domain.model.PhotoEntity
import com.example.pexelsapp.domain.usecase.GetCuratedPhotosUC
import com.example.pexelsapp.domain.usecase.GetFeatureCollectionsUC
import com.example.pexelsapp.domain.usecase.GetPhotosByCategoryUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosByCategoryUC: GetPhotosByCategoryUC,
    private val getFeatureCollectionsUC: GetFeatureCollectionsUC,
    private val getCuratedPhotosUC: GetCuratedPhotosUC
) : ViewModel() {

    private val _data: MutableState<DataOrException<List<PhotoEntity>, Boolean, Exception>> =
        mutableStateOf(DataOrException(listOf(), false, Exception("")))
    val data: MutableState<DataOrException<List<PhotoEntity>, Boolean, Exception>> = _data

    private val _featureCollections: MutableState<DataOrException<List<FeatureEntity>, Boolean, Exception>> =
        mutableStateOf(DataOrException(emptyList(), false, Exception("")))
    val featureCollections: MutableState<DataOrException<List<FeatureEntity>, Boolean, Exception>> = _featureCollections

    private val selectedCategoryTitle: MutableState<String?> = mutableStateOf(null)

    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

    init {
        getFeatureCollections()
        getCuratedPhotos()
    }

    fun getPhotosByCategory(category: String, page: Int, perPage: Int) = viewModelScope.launch {
        _data.value = DataOrException(_data.value.data, true, _data.value.e)

        try {
            getPhotosByCategoryUC(category, page, perPage).collect { photos ->
                _data.value = DataOrException(photos, false, null)
            }
            updateSelectedCategory(category)
        } catch (e: Exception) {
            _data.value = DataOrException(emptyList(), false, e)
        }
    }

    fun getFeatureCollections() = viewModelScope.launch {
        _featureCollections.value = DataOrException(_featureCollections.value.data, true, _featureCollections.value.e)
        try {
            val collections = getFeatureCollectionsUC()
            _featureCollections.value = DataOrException(collections, false, null)
            _isDataLoaded.value = true
        } catch (e: Exception) {
            _featureCollections.value = DataOrException(emptyList(), false, e)
            _isDataLoaded.value = true
        }
    }


    private fun updateSelectedCategory(selectedTitle: String?) {
        selectedCategoryTitle.value = selectedTitle
        _featureCollections.value.data?.forEach { category ->
            category.isSelected = category.searchString == selectedTitle
        }
        _featureCollections.value = _featureCollections.value
    }

    fun getCuratedPhotos() = viewModelScope.launch {
        _data.value = DataOrException(_data.value.data, true, _data.value.e)
        try {
            getCuratedPhotosUC().collect { photos ->
                _data.value = DataOrException(photos, false, null)
            }
        } catch (e: Exception) {
            _data.value = DataOrException(emptyList(), false, e)
        }
    }
}
