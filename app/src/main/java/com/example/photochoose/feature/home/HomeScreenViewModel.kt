package com.example.photochoose.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val _resultCount = MutableStateFlow(50)
    val resultCount = _resultCount.asStateFlow()


    fun setResultCount(count: Int) {
        _resultCount.update { count }
    }

}