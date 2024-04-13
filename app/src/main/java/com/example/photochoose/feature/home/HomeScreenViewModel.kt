package com.example.photochoose.feature.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _selectedUris = MutableStateFlow<List<Uri>>(listOf())
    val selectedUris = _selectedUris.asStateFlow()

    fun setSelectedImages(selectedUri: List<Uri>) {
        _selectedUris.update { selectedUri }
    }

}