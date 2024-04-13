package com.example.photochoose.feature.choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photochoose.data.photo.PhotoRepository
import com.example.photochoose.data.photo.model.ImageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoosePhotoScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos: MutableStateFlow<List<ImageInfo>> = MutableStateFlow(listOf())
    val photos = _photos.asStateFlow()

    private val _selectedPhotos: MutableStateFlow<List<ImageInfo>> = MutableStateFlow(listOf())
    val selectedPhotos = _selectedPhotos.asStateFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _photos.update { photoRepository.getImages() }
        }
    }

    fun selectDisSelectImage(photo: ImageInfo) {
        if (photo in selectedPhotos.value) {
            _selectedPhotos.update { it - photo }
        } else {
            if (selectedPhotos.value.size < 2) {
                _selectedPhotos.update { it + photo }
            }
        }
    }

    companion object {
        const val NUM_OF_PHOTO_TO_CHOOSE = 2
    }
}
